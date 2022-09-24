package cs.miu.edu;

import java.util.function.Consumer;

/**
 * @author Prabhat Gyawali
 * @created 23-Sep-2022 - 11:19 AM
 * @project BigDataProject
 */
public interface ConsumerWithException<T> {
    void acceptThrows(T t) throws Exception;

    static <T> Consumer<T> accept(ConsumerWithException<T> consumerWithException) {
        return val -> {
            try {
                consumerWithException.acceptThrows(val);
            } catch (Exception e) {
            	System.out.println(val);
            	System.err.println(e);
            	
            	
                //throw new RuntimeException(e);
            }
        };

    }
}
