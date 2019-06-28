package com.designpattern;

/**
 * Chain of Responsiblity 패턴에서는 문제 해결사들이 한줄로 쫙 서있다가 문제가 들어오면,
 * 자기가 해결할 수 있으면 해결하고, 안 되면 다음 해결사에게 문제를 넘겨버립니다.
 */

public class DP9ChainOfResponsibility {
    public static void main(String[] args) {

        Problem[] problems = new Problem[5];
        problems[0] = new Problem("덩치 큰 깡패");
        problems[1] = new Problem("컴퓨터 보안장치");
        problems[2] = new Problem("까칠한 여자");
        problems[3] = new Problem("날렵한 깡패");
        problems[4] = new Problem("폭탄");

        Expert fighter = new Fighter();
        Expert hacker = new Hacker();
        Expert casanova = new Casanova();

        fighter.setNextExpert(hacker).setNextExpert(casanova);


        for (Problem problem : problems) {
            fighter.support(problem);
        }

    }
}

/**
 * 전문가: 상위 클래스
 */
abstract class Expert{

    private Expert nextExpert;

    protected String expertName;

    public final void support(Problem p){
        if(solve(p)){
            System.out.println(expertName+ "이(가) " + p.getProblemName()  +"을(를) 해결해 버렸네.");
        }else{
            if (nextExpert != null) {
                nextExpert.support(p);
            }else{
                System.out.println(p.getProblemName() + "은(는) 해결할 놈이 없네 ㅡㅡ.");
            }

        }
    }

    public Expert setNextExpert(Expert nextExpert){
        this.nextExpert = nextExpert;
        return nextExpert;
    }

    //solve()는 각각의 개별 클래스별로 자기가 해결 가능한지 불가능한지를 판단하는 매쏘드입니다.
    // 당연히 구체적으로 기술해야 하므로 하위 객체에 떠넘깁니다.
    protected abstract boolean solve(Problem p);
}

/**
 * 전문가들이 풀어야 할 문제 클래스
 */
class Problem{
    private String problemName;

    public Problem(String name){
        this.problemName = name;
    }

    public String getProblemName(){
        return problemName;
    }
}

/**
 * 첫번째 전문가 fighter
 */
class Fighter extends Expert{
    public Fighter(){
        this.expertName = "격투가";
    }

    @Override
    protected boolean solve(Problem p) {
        return p.getProblemName().contains("깡패");
    }
}


class Hacker extends Expert{

    public Hacker(){
        this.expertName = "해커";
    }

    @Override
    protected boolean solve(Problem p) {
        return p.getProblemName().contains("컴퓨터");
    }
}

class Casanova extends Expert{
    public Casanova(){
        expertName = "카사노바";
    }

    @Override
    protected boolean solve(Problem p) {
        return p.getProblemName().contains("여자") || p.getProblemName().contains("여성");
    }
}