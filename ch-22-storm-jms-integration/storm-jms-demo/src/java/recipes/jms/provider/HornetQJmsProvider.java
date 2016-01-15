package recipes.jms.provider;
import javax.jms.ConnectionFactory; import javax.jms.Destination;
import recipes.jms.provider.ProviderState; import backtype.storm.contrib.jms.JmsProvider;
/**
* Needs to be completely stateless so it can be serialized. */
public class HornetQJmsProvider implements JmsProvider {
private static final long serialVersionUID = -1260267361423423454L;
public ConnectionFactory connectionFactory() { return ProviderState.getCF();
}
public Destination destination() {
return ProviderState.getDestination();
} }

