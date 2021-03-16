import java.io.Serializable;
import java.util.Random;

public class Gate implements Serializable {
    private String gate;

    public Gate() {
        this.gate = generateGate();
    }

    public Gate(String gate) {
        this.gate = gate;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String generateGate() {
        String gate = "";
        Random random = new Random();
        char c = (char)(random.nextInt(26) + 'A');
        gate = gate + c;
        int integer = random.nextInt(99);
        return gate + integer;
    }

}
