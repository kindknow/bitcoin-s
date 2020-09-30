package org.bitcoins.dlc.oracle.storage

import org.bitcoins.core.hd._
import org.bitcoins.crypto.{SchnorrDigitalSignature, SchnorrNonce}

case class RValueDb(
    nonce: SchnorrNonce,
    label: String,
    purpose: HDPurpose,
    accountCoin: HDCoinType,
    accountIndex: Int,
    chainType: Int,
    keyIndex: Int,
    commitmentSignature: SchnorrDigitalSignature) {

  val path: BIP32Path = BIP32Path.fromString(
    s"m/${purpose.constant}'/${accountCoin.toInt}'/$accountIndex'/$chainType'/$keyIndex'")
}

object RValueDbHelper {

  def apply(
      nonce: SchnorrNonce,
      label: String,
      account: HDAccount,
      chainType: Int,
      keyIndex: Int,
      commitmentSignature: SchnorrDigitalSignature): RValueDb = {
    RValueDb(nonce,
             label,
             account.purpose,
             account.coin.coinType,
             account.index,
             chainType,
             keyIndex,
             commitmentSignature)
  }
}