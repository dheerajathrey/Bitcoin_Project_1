package in.ac.iitk.cse.cs252.transactions;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import java.io.File;
import java.math.BigInteger;
import java.net.UnknownHostException;

import static org.bitcoinj.script.ScriptOpCodes.*;

/**
 * Created by bbuenz on 24.09.15.
 */
public class LinearEquationTransaction extends ScriptTransaction {
    // TODO: Problem 2
    public LinearEquationTransaction(NetworkParameters parameters, File file, String password) {
        super(parameters, file, password);
    }

    @Override
    public Script createInputScript() {
        // TODO: Create a script that can be spend by two numbers x and y such that x+y=first 4 digits of your iitk roll and |x-y|=last 4 digits of your suid (perhaps +1)
    	ScriptBuilder builder = new ScriptBuilder();
    	BigInteger Sum  = BigInteger.valueOf(150);
        BigInteger Diff  = BigInteger.valueOf(232);
    	builder.op(OP_2DUP);
    	builder.op(OP_ADD);
    	builder.data(encode(Sum));
    	builder.op(OP_EQUALVERIFY);
    	builder.op(OP_SUB);
    	builder.data(encode(Diff));
    	builder.op(OP_EQUAL);
    	 	
    	
    	return builder.build();
    }

    @Override
    public Script createRedemptionScript(Transaction unsignedScript) {
        // TODO: Create a spending script
    	ScriptBuilder builder = new ScriptBuilder();
    	BigInteger x = BigInteger.valueOf(191);
        BigInteger y = BigInteger.valueOf(-41);
    	builder.data(encode(x));
    	builder.data(encode(y));  	
    	return builder.build();
    }

    private byte[] encode(BigInteger bigInteger) {
        return Utils.reverseBytes(Utils.encodeMPI(bigInteger, false));
    }
}
