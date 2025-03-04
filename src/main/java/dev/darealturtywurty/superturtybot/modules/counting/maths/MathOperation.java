package dev.darealturtywurty.superturtybot.modules.counting.maths;

import static dev.darealturtywurty.superturtybot.modules.counting.maths.OperationType.FLOAT;
import static dev.darealturtywurty.superturtybot.modules.counting.maths.OperationType.INT;

import java.util.Arrays;
import java.util.List;

public enum MathOperation {
    ADD("%.1f + %.1f", INT), SUBTRACT("%.1f - %.1f", INT), MULTIPLY("%.1f * %.1f", INT), DIVIDE("%.1f / %.1f", INT),
    MODULO("%.1f %% %.1f", INT), SQUARE("%.1f ^ 2", INT), SQRT("√%.1f", INT), FLOOR("⌊%.1f⌋", FLOAT), ROUND("[%.1f]", FLOAT),
    CEIL("⌈%.1f⌉", FLOAT);
    
    private final String format;
    private final OperationType operationType;
    
    MathOperation(String format, OperationType opType) {
        this.format = format;
        this.operationType = opType;
    }
    
    public String getFormat() {
        return this.format;
    }
    
    public OperationType getOperationType() {
        return this.operationType;
    }
    
    public static List<MathOperation> getFloats() {
        return getOfType(OperationType.FLOAT);
    }
    
    public static List<MathOperation> getInts() {
        return getOfType(OperationType.INT);
    }
    
    private static List<MathOperation> getOfType(OperationType type) {
        return Arrays.stream(MathOperation.values()).filter(op -> op.getOperationType() == type).toList();
    }
}
