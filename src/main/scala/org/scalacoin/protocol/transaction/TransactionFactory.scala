package org.scalacoin.protocol.transaction

import org.scalacoin.marshallers.transaction.{RawTransactionParser, RawTransactionInputParser}
import org.scalacoin.util.Factory

/**
 * Created by chris on 2/21/16.
 */
trait TransactionFactory extends Factory[Transaction] {


  /**
   * Updates a transaction outputs
   * @param updatedOutputs
   * @return
   */
  def factory(oldTx : Transaction, updatedOutputs : UpdateTransactionOutputs) : Transaction = {
    TransactionImpl(oldTx.version,oldTx.inputs,updatedOutputs.outputs,oldTx.lockTime)
  }

  /**
   * Updates a transaction's inputs
   * @param updatedInputs
   * @return
   */
  def factory(oldTx : Transaction,updatedInputs : UpdateTransactionInputs) : Transaction = {
    TransactionImpl(oldTx.version,updatedInputs.inputs,oldTx.outputs,oldTx.lockTime)
  }

  /**
   * Factory function that modifies a transactions locktime
   * @param oldTx
   * @param lockTime
   * @return
   */
  def factory(oldTx : Transaction, lockTime : Long) : Transaction = {
    TransactionImpl(oldTx.version,oldTx.inputs,oldTx.outputs,lockTime)
  }


  /**
   * Removes the inputs of the transactions
   * @return
   */
  def emptyInputs(oldTx : Transaction) : Transaction = TransactionImpl(oldTx.version,Seq(),oldTx.outputs,oldTx.lockTime)

  /**
   * Removes the outputs of the transactions
   * @return
   */
  def emptyOutputs(oldTx : Transaction) : Transaction = TransactionImpl(oldTx.version,oldTx.inputs,Seq(),oldTx.lockTime)



  def factory(bytes : Array[Byte]) : Transaction = fromBytes(bytes.toSeq)

  def fromBytes(bytes : Seq[Byte]) : Transaction = RawTransactionParser.read(bytes)

}

object TransactionFactory extends TransactionFactory
sealed trait TransactionFactoryHelper
case class UpdateTransactionOutputs(outputs : Seq[TransactionOutput]) extends TransactionFactoryHelper
case class UpdateTransactionInputs(inputs : Seq[TransactionInput]) extends TransactionFactoryHelper
