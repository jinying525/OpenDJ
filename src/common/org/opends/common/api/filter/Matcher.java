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
 *      Copyright 2009 Sun Microsystems, Inc.
 */

package org.opends.common.api.filter;



import org.opends.common.api.Entry;



/**
 * An interface for determining whether entries match a {@code Filter}.
 */
public final class Matcher
{
  /**
   * Indicates whether this filter {@code Matcher} matches the provided
   * {@code Entry}.
   *
   * @param entry
   *          The entry to be matched.
   * @return {@code true} if this filter {@code Matcher} matches the
   *         provided {@code Entry}.
   */
  public boolean matches(Entry entry)
  {
    // TODO: not yet implemented.
    return true;
  }
}
