/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at
 * trunk/opends/resource/legal-notices/OpenDS.LICENSE
 * or https://OpenDS.dev.java.net/OpenDS.LICENSE.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at
 * trunk/opends/resource/legal-notices/OpenDS.LICENSE.  If applicable,
 * add the following below this CDDL HEADER, with the fields enclosed
 * by brackets "[]" replaced with your own identifying information:
 *      Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 *
 *      Copyright 2006-2010 Sun Microsystems, Inc.
 *      Portions copyright 2012-2013 ForgeRock AS.
 */
package org.opends.server.replication.server;

import java.util.SortedMap;
import java.util.TreeMap;

import org.opends.messages.Message;
import org.opends.server.replication.common.CSN;
import org.opends.server.replication.protocol.UpdateMsg;

import static org.opends.messages.ReplicationMessages.*;
import static org.opends.server.loggers.ErrorLogger.*;

/**
 * This class is used to build ordered lists of UpdateMsg.
 * The order is defined by the order of the CSN of the UpdateMsg.
 */
public class MsgQueue
{
  private SortedMap<CSN, UpdateMsg> map = new TreeMap<CSN, UpdateMsg>();
  private final Object lock = new Object();

  /** The total number of bytes for all the message in the queue. */
  private int bytesCount = 0;

  /**
   * Return the first UpdateMsg in the MsgQueue.
   *
   * @return The first UpdateMsg in the MsgQueue.
   */
  public UpdateMsg first()
  {
    synchronized (lock)
    {
      return map.get(map.firstKey());
    }
  }

  /**
   * Returns the number of elements in this MsgQueue.
   *
   * @return The number of elements in this MsgQueue.
   */
  public int count()
  {
    synchronized (lock)
    {
      return map.size();
    }
  }

  /**
   * Returns the number of bytes in this MsgQueue.
   *
   * @return The number of bytes in this MsgQueue.
   */
  public int bytesCount()
  {
    synchronized (lock)
    {
      return bytesCount;
    }
  }

  /**
   * Returns <tt>true</tt> if this MsgQueue contains no UpdateMsg.
   *
   * @return <tt>true</tt> if this MsgQueue contains no UpdateMsg.
   */
  public boolean isEmpty()
  {
    synchronized (lock)
    {
      return map.isEmpty();
    }
  }


  /**
   * Add an UpdateMsg to this MessageQueue.
   *
   * @param update The UpdateMsg to add to this MessageQueue.
   */
  public void add(UpdateMsg update)
  {
    synchronized (lock)
    {
      UpdateMsg msgSameCSN = map.put(update.getCSN(), update);
      if (msgSameCSN != null)
      {
        try
        {
          if (msgSameCSN.getBytes().length != update.getBytes().length
              || msgSameCSN.isAssured() != update.isAssured()
              || msgSameCSN.getVersion() != update.getVersion())
          {
            // Adding 2 msgs with the same CSN is ok only when
            // the 2 msgs are the same
            bytesCount += (update.size() - msgSameCSN.size());
            Message errMsg = ERR_RSQUEUE_DIFFERENT_MSGS_WITH_SAME_CN.get(
                msgSameCSN.getCSN().toString(),
                msgSameCSN.toString(), update.toString());
            logError(errMsg);
          }
        }
        catch(Exception e)
        {}
      }
      else
      {
        // it is really an ADD
        bytesCount += update.size();
      }
    }
  }

  /**
   * Get and remove the first UpdateMsg in this MessageQueue.
   *
   * @return The first UpdateMsg in this MessageQueue.
   */
  public UpdateMsg removeFirst()
  {
    synchronized (lock)
    {
      UpdateMsg update = map.get(map.firstKey());
      map.remove(update.getCSN());
      bytesCount -= update.size();
      if ((map.size() == 0) && (bytesCount != 0))
      {
        // should never happen
        Message msg = ERR_BYTE_COUNT.get(Integer.toString(bytesCount));
        logError(msg);
        bytesCount = 0;
      }
      return update;
    }
  }

  /**
   * Returns <tt>true</tt> if this map contains an UpdateMsg
   * with the same CSN as the given UpdateMsg.
   *
   * @param msg UpdateMsg whose presence in this queue is to be tested.
   *
   * @return <tt>true</tt> if this map contains an UpdateMsg
   *         with the same CSN as the given UpdateMsg.
   *
   */
  public boolean contains(UpdateMsg msg)
  {
    synchronized (lock)
    {
      return map.containsKey(msg.getCSN());
    }
  }

  /**
   * Removes all UpdateMsg form this queue.
   */
  public void clear()
  {
    synchronized (lock)
    {
      map.clear();
      bytesCount = 0;
    }
  }
}