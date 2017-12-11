import java.io.*;

/**
 * @author luqibao
 * @date 2017/11/16
 */
public class SeriaClass extends SupperSeriaClass implements Serializable {

	private int idx;

	/**
	 * 反序列的时候必须提供一个空的构造函数
	 */
	public SeriaClass() {
		System.out.println("construction 1");
	}

	public SeriaClass(int idx) {
		super(2);
		this.idx = idx;

	}

	public int getIdx() {

		return idx;
	}

	private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
		inputStream.defaultReadObject();
		int idx = inputStream.readInt();
		this.idx = idx;
		System.out.println("read obj ... ");
	}

	private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
		objectOutputStream.defaultWriteObject();
		objectOutputStream.writeInt(this.idx);

	}

	/**
	 * 在序列化完之后执行
	 * 
	 * @return
	 */
	private Object readResolve() {

		System.out.println(" resolve ... ");

		return this;
	}

	@Override
	public String toString() {

		return " idx = " + idx;
	}

	public static void main(String[] args) throws Exception {
		seria();

		deseria();
	}

	private static void seria() throws Exception {
		SeriaClass seriaClass = new SeriaClass(1);
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("seria.clz"));
		outputStream.writeObject(seriaClass);
		outputStream.flush();
		outputStream.close();
	}

	private static void deseria() throws Exception {

		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("seria.clz"));
		SeriaClass seriaClass = (SeriaClass) inputStream.readObject();

		System.out.println(seriaClass);

	}
}
