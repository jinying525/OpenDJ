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
 *      Copyright 2013 ForgeRock AS
 */
package org.opends.server.replication.server.changelog.api;

import org.opends.server.replication.common.CSN;

/**
 * The Change Number Index Data class represents records stored in the
 * {@link ChangeNumberIndexDB}.
 */
public class CNIndexRecord
{

  /** This is the key used to store the rest of the . */
  private long changeNumber;
  private String previousCookie;
  private String baseDN;
  private CSN csn;

  /**
   * Builds an instance of this class.
   *
   * @param changeNumber
   *          the change number
   * @param previousCookie
   *          the previous cookie
   * @param baseDN
   *          the baseDN
   * @param csn
   *          the replication CSN field
   */
  public CNIndexRecord(long changeNumber, String previousCookie, String baseDN,
      CSN csn)
  {
    super();
    this.changeNumber = changeNumber;
    this.previousCookie = previousCookie;
    this.baseDN = baseDN;
    this.csn = csn;
  }

  /**
   * Getter for the baseDN field.
   *
   * @return the baseDN
   */
  public String getBaseDN()
  {
    return baseDN;
  }

  /**
   * Getter for the replication CSN field.
   *
   * @return The replication CSN field.
   */
  public CSN getCSN()
  {
    return csn;
  }

  /**
   * Getter for the change number field.
   *
   * @return The change number field.
   */
  public long getChangeNumber()
  {
    return changeNumber;
  }

  /**
   * Get the previous cookie field.
   *
   * @return the previous cookie.
   */
  public String getPreviousCookie()
  {
    return previousCookie;
  }

  /** {@inheritDoc} */
  @Override
  public String toString()
  {
    return "changeNumber=" + changeNumber + " csn=" + csn + " baseDN=" + baseDN
        + " previousCookie=" + previousCookie;
  }
}