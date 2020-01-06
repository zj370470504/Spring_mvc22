package socket.wechar;

public class Test {
    public void DD(RunnableTest runnableTest){
        runnableTest.asd();
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.DD(() -> {
            System.out.println("ggg");
        });
        test.DD(new RunnableTest() {
            @Override
            public void asd() {
                System.out.println("fff");
            }
        });
    }
}
