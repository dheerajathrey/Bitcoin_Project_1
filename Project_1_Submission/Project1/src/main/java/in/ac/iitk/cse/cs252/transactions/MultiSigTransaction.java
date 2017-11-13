package in.ac.iitk.cse.cs252.transactions;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import java.io.File;
import java.math.BigInteger;

import static org.bitcoinj.script.ScriptOpCodes.*;

/**
 * Created by bbuenz on 24.09.15.
 */
public class MultiSigTransaction extends ScriptTransaction {
    // TODO: Problem 3
	private DeterministicKey key_Bank, keyP, keyQ, keyR;
    public MultiSigTransaction(NetworkParameters parameters, File file, String password) {
        super(parameters, file, password);
        key_Bank = getWallet().freshReceiveKey();
        keyP = getWallet().freshReceiveKey();
        keyQ = getWallet().freshReceiveKey();
        keyR = getWallet().freshReceiveKey();
    }

    @Override
    public Script createInputScript() {
        // TODO: Create a script that can be spend using signatures from the bank and one of the customers
    	ScriptBuilder builder = new ScriptBuilder();
    	builder.op(OP_1);
    	builder.data(keyP.getPubKey());
    	builder.data(keyQ.getPubKey());
    	builder.data(keyR.getPubKey());
    	builder.op(OP_3);
    	builder.op(OP_CHECKMULTISIGVERIFY);
    	builder.data(key_Bank.getPubKey());
    	builder.op(OP_CHECKSIG);    	
        return builder.build();
    }

    @Override
    public Script createRedemptionScript(Transaction unsignedTransaction) {
        // Please be aware of the CHECK_MULTISIG bug!
        // TODO: Create a spending script
    	TransactionSignature txSig_Bank = sign(unsignedTransaction, key_Bank);
    	TransactionSignature txSigP = sign(unsignedTransaction, keyP);
//    	TransactionSignature txSigQ = sign(unsignedTransaction, keyQ);
//    	TransactionSignature txSigR = sign(unsignedTransaction, keyR);
    	
    	ScriptBuilder builder = new ScriptBuilder();
    	builder.data(txSig_Bank.encodeToBitcoin());
    	builder.op(OP_1);
    	builder.data(txSigP.encodeToBitcoin());
    	
        return builder.build();
    }
}
