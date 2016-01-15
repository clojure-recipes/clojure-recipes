package recipes.jms.provider;
import javax.jms.ConnectionFactory; import javax.jms.Destination;
/**
* Class using static members for state holding to get around serialization. *
*/
public class ProviderState {
private static ConnectionFactory cf;
private static Destination dest;
public static void setCF(ConnectionFactory connectionFactory) {cf = connectionFactory;}
public static void setDestination(Destination destination) {dest = destination;}
public static ConnectionFactory getCF() {return cf;}
public static Destination getDestination() {return dest;} }

