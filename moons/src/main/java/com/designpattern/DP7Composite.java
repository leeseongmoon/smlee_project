package com.designpattern;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
    파일데이터와 같은 일반적인 트리 구조의 데이터 타입을 만드는 것이 Composite 패턴
    등장인물 : 첫째, 상위컴포넌트
             둘째, 상위컴포넌트를 상속, 자식컴포넌트를 가질수 있는 Composite
            셋째, 상위컴포넌트를 상속, 자식컴포넌트를 가질수 없는 Leaf
    --> 디렉터리가 Composite라면 파일은 Leaf
 */

public class DP7Composite {

    public static void main(String[] args) {

        Composite main = new Composite("Main");
        Composite sub1 = new Composite("sub1");
        Composite sub11 = new Composite("sub11");
        Composite sub12 = new Composite("sub12");
        Leaf leaf121 = new Leaf("leaf121");
        Composite sub13 = new Composite("sub13");
        Leaf leaf14 = new Leaf("leaf14");

        Composite sub2 = new Composite("sub2");
        Composite sub21 = new Composite("sub21");
        Composite sub22 = new Composite("sub22");

        /*
        Composite main = new Composite("Main");
        Composite sub1 = new Composite("sub1");
        Composite sub2 = new Composite("sub2");
        Composite sub11 = new Composite("sub11");
        Composite sub12 = new Composite("sub12");
        Composite sub13 = new Composite("sub13");
        Composite sub21 = new Composite("sub21");
        Composite sub22 = new Composite("sub22");
        Leaf leaf14 = new Leaf("leaf14");
        Leaf leaf121 = new Leaf("leaf121");

         */

        main.add(sub1);
        main.add(sub2);
        sub1.add(sub11);
        sub1.add(sub12);
        sub1.add(sub13);
        sub2.add(sub21);
        sub2.add(sub22);
        sub1.add(leaf14);
        sub12.add(leaf121);

        System.out.println(main.getString());


    }
}

/**
 * 상위 컴포넌트
 * Component는 멤버변수로 List<Component>를 가진다. <-- 트리구조를 만드는 포인트
 */
abstract class Component{

    private String componentName;

    protected List<Component> children = new ArrayList<Component>();

    public Component(String componentName){
        this.componentName = componentName;
    }

    public String getComponentName(){
        return componentName;
    }

    public abstract void add(Component c);

    public List<Component> getChildren(){
        return children;
    }

    public String getString(){
        return getString(0);
    }

    private String getString(int depth){
        StringBuffer sb = new StringBuffer();
        if(this instanceof Composite){
            for(int i=0; i < depth; i++){
                sb.append(" ");
            }
            sb.append("+"+getComponentName()+"\n");

            for(Component comp : children){
                sb.append(comp.getString(depth+1));
            }
        }else{
            for(int i=0; i<depth; i++){
                sb.append(" ");
            }
            sb.append("-"+getComponentName()+"\n");
        }
        return sb.toString();
    }
}


/**
 * 하위 composite(하위 노드 가질 수 있음)
 */
class Composite extends Component{

    public Composite(String componentName){
        super(componentName);
    }

    @Override
    public void add(Component c) {
        children.add(c);
    }
}

/**
 * 하위 Leaf (하위 노드 가질 수 없음)
 */
class Leaf extends Component{

    public Leaf(String componentName){
        super(componentName);
    }

    @Override
    public void add(Component c) {
        throw new UnsupportedOperationException();
    }
}