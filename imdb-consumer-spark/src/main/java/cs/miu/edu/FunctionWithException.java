package cs.miu.edu;

import java.util.function.Function;

/**
 * @author Prabhat Gyawali
 * @created 23-Sep-2022 - 11:24 AM
 * @project BigDataProject
 */
@FunctionalInterface
public interface FunctionWithException<T, R> {
    R applyThrows(T t) throws Exception;

    static<T, R> Function<T, R> apply(FunctionWithException<T, R> functionWithException) {
        return t-> {
            try{
               return functionWithException.applyThrows(t);
            }catch (Exception ex){
            	System.out.println(t);
            	System.err.println(ex);
            	return null;
//                throw new RuntimeException(ex);
            }
        };
    }


}

