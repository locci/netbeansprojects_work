/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testeboolean;

import com.bpodgursky.jbool_expressions.And;
import static com.bpodgursky.jbool_expressions.ExprUtil.expr;
import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.Not;
import com.bpodgursky.jbool_expressions.Or;
import com.bpodgursky.jbool_expressions.Variable;
import com.bpodgursky.jbool_expressions.parsers.ExprParser;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.TreeMethodExpression;
import de.odysseus.el.TreeValueExpression;
import de.odysseus.el.misc.TypeConverter;
import de.odysseus.el.tree.Tree;
import de.odysseus.el.tree.TreeBuilder;
import de.odysseus.el.tree.TreeStore;
import de.odysseus.el.tree.impl.Builder;
import de.odysseus.el.util.SimpleContext;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.mvel2.MVEL;
import org.mvel2.Macro;
import org.mvel2.ParserConfiguration;
import recoder.java.expression.Literal;
import recoder.java.expression.literal.BooleanLiteral;


//import de.odysseus.el.ExpressionFactoryImpl;
//import de.odysseus.el.TreeValueExpression;
//import de.odysseus.el.util.SimpleContext;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import javax.el.ExpressionFactory;
//import javax.el.ValueExpression;
//import org.apache.commons.jexl2.JexlEngine;




/**
 *
 * @author alexandre
 */
public class TesteBoolean {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ScriptException, NoSuchMethodException, IOException {
        // TODO code application logic here
        
       //Teste da JUEL Java Unified Expression*************
//        ExpressionFactoryImpl factory2 = new ExpressionFactoryImpl();
//        SimpleContext context = new SimpleContext(); // more on this here...       
//        TreeValueExpression e = factory2.createValueExpression(context, "${((field.getAccessFlags() && (IAccessFlags.ACC_STATIC || IAccessFlags.ACC_FINAL)) == (IAccessFlags.ACC_STATIC || IAccessFlags.ACC_FINAL))}", Object.class);
//        PrintWriter out = new PrintWriter(new FileWriter("log"));  
//        e.dump(out);
//        out.flush();
        //Problema: não trabalha com todos os operadore | e &. Não dá uma lista de variáveis.
        //JUEL*************************************************
       
//        ExpressionFactoryImpl factory = new ExpressionFactoryImpl();
//
//        TreeMethodExpression e2 =   factory.createMethodExpression(context, "#{x.toString}", String.class, new Class[]{});
//        PrintWriter out2 = new PrintWriter(System.out);
//        e2.dump(out2);
//
//        out2.flush();
//        System.out.println(e.isDeferred()); // true
        
      
        
        
    }
    
    
}
