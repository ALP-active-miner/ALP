package de.tu_darmstadt.stg.mudetect.aug.visitors;

import de.tu_darmstadt.stg.mudetect.aug.model.actions.*;
import de.tu_darmstadt.stg.mudetect.aug.model.controlflow.*;
import de.tu_darmstadt.stg.mudetect.aug.model.data.*;
import de.tu_darmstadt.stg.mudetect.aug.model.dataflow.DefinitionEdge;
import de.tu_darmstadt.stg.mudetect.aug.model.dataflow.ParameterEdge;
import de.tu_darmstadt.stg.mudetect.aug.model.dataflow.QualifierEdge;
import de.tu_darmstadt.stg.mudetect.aug.model.dataflow.ReceiverEdge;
import school.first_author.LiteralsUtils;

public class BaseAUGLabelProvider implements AUGLabelProvider {
    @Override
    public String visit(ContainsEdge edge) {
        return "contains";
    }

    @Override
    public String visit(ExceptionHandlingEdge edge) {
        return "hdl";
    }

    @Override
    public String visit(FinallyEdge edge) {
        return "finally";
    }

    @Override
    public String visit(OrderEdge edge) {
        return "order";
    }

    @Override
    public String visit(RepetitionEdge edge) {
        return "rep";
    }

    @Override
    public String visit(SelectionEdge edge) {
        return "sel";
    }

    @Override
    public String visit(SynchronizationEdge edge) {
        return "sync";
    }

    @Override
    public String visit(ThrowEdge edge) {
        return "throw";
    }

    @Override
    public String visit(DefinitionEdge edge) {
        return "def";
    }

    @Override
    public String visit(ParameterEdge edge) {
        return "para";
    }

    @Override
    public String visit(QualifierEdge edge) {
        return "qual";
    }

    @Override
    public String visit(ReceiverEdge edge) {
        return "recv";
    }

    @Override
    public String visit(ArrayAccessNode node) {
        return visit((MethodCallNode) node);
    }

    @Override
    public String visit(ArrayAssignmentNode node) {
        return visit((MethodCallNode) node);
    }

    @Override
    public String visit(ArrayCreationNode node) {
        return "{" + node.getDeclaringTypeName() + "}";
    }

    @Override
    public String visit(AssignmentNode node) {
        return "=";
    }

    @Override
    public String visit(BreakNode node) {
        return "<break>";
    }

    @Override
    public String visit(CastNode node) {
        return node.getTargetType() + ".<cast>";
    }

    @Override
    public String visit(CatchNode node) {
        return "<catch>";
    }

    @Override
    public String visit(ConstructorCallNode node) {
        return visit((MethodCallNode) node);
    }

    @Override
    public String visit(ContinueNode node) {
        return "<continue>";
    }

    @Override
    public String visit(InfixOperatorNode node) {
        return visit((OperatorNode) node);
    }

    @Override
    public String visit(MethodCallNode node) {
        return node.getDeclaringTypeName() + "." + node.getMethodSignature();
    }

    @Override
    public String visit(NullCheckNode node) {
        return visit((InfixOperatorNode) node);
    }

    private String visit(OperatorNode node) {
        return node.getOperator();
    }

    @Override
    public String visit(ReturnNode node) {
        return "<return>";
    }

    @Override
    public String visit(SuperConstructorCallNode node) {
        return node.getDeclaringTypeName() + "()";
    }

    @Override
    public String visit(ThrowNode node) {
        return "<throw>";
    }

    @Override
    public String visit(TypeCheckNode node) {
        return node.getTargetTypeName() + ".<instanceof>";
    }

    @Override
    public String visit(UnaryOperatorNode node) {
        return visit((OperatorNode) node);
    }

    @Override
    public String visit(AnonymousClassMethodNode node) {
        return node.getName();
    }

    @Override
    public String visit(AnonymousObjectNode node) {
        return node.getType();
    }

    @Override
    public String visit(ConstantNode node) {
        return node.getType() + ":" + node.getName();
    }

    @Override
    public String visit(ExceptionNode node) {
        return node.getType();
    }

    @Override
    public String visit(LiteralNode node) {
    	String literalString = node.getType() + ":" + node.getValue(); 
    			
    	// if not String, long, int, just return both type and value
    	if (!(node.getType().equals("String") || node.getType().equals("long") || node.getType().equals("int"))) {
    		return literalString;
    	}
    	// Otherwise, return both type and value only if frequent enough
    	if (LiteralsUtils.getFreq(node.getValue()) >= 10) {
    		return literalString;
    	} 
    	
    	// or just return its type.
		return node.getType();
    }

    @Override
    public String visit(VariableNode node) {
        return node.getType();
    }
}
