package com.designpattern;

/**
 * Decorator는 메소드의 확장개념이다.
 * 멤버변수로 받은 객체의 메소드를 이용하여 그 메소드를 확장하는 것이다.
 *
 * java.io 에 있는 InputStream, Reader, OutputStream, Writer 등은 모두 Decorator 패턴으로 구성되어 있다.
 *
 *
 * Reader reader = new FileReader("파일명");
 * Reader reader = new BufferedReader(new FileReader("파일명");
 * 파일은 위의 두가지 방법으로 모두 읽을 수 있습니다. 둘다 Reader의 형식으로 받습니다.
 * BufferedReader의 생성자는 Reader를 받아 멤버 변수로 가지고 있으며, Reader를 상속 받습니다.
 * 멤버 변수로 받은 Reader를 이용하여, 버퍼를 이용해서 읽습니다.
 *
 */
public class DP8Decorator {

    public static void main(String[] args) {

        Decorator decorator = new Decorator();
        System.out.println(decorator.getMerong());

        Decorator child = new ChildDecorator(decorator);
        System.out.println(child.getMerong());

        Decorator child2 = new ChildDecorator(child);
        System.out.println(child2.getMerong());

        Decorator child3 = new ChildDecorator(child2);
        System.out.println(child3.getMerong());
    }
}

class Decorator{
    public String getMerong(){
        return "merong";
    }
}

class ChildDecorator extends Decorator{

    // 상위클래스의 형식을 멤버변수로 가진다.
    private Decorator decorator;

    public ChildDecorator(Decorator decorator){
        this.decorator = decorator;
    }

    @Override
    public String getMerong() {
        return "@"+decorator.getMerong()+"@";
    }
}
