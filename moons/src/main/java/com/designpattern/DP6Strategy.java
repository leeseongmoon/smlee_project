package com.designpattern;

public class DP6Strategy {

    public static void main(String[] args) {
        Seller cupSeller = new CupSeller();
        Seller phoneSeller = new PhoneSeller();

        Mart mart1 = new Mart(cupSeller);
        mart1.order();

        Mart mart2 = new Mart(phoneSeller);
        mart2.order();
    }

}

/**
 * 상위 인터페이스
 */
interface Seller{
    public void sell();
}

/**
 인터페이스 구현체1
 */
class CupSeller implements Seller{
    public void sell() {
        System.out.println("cup sell");
    }
}

/**
 * 인터페이스 구현체2
 */
class PhoneSeller implements Seller{
    public void sell() {
        System.out.println("phone sell");
    }
}

/**
 * 인터페이스 사용하는 클래스
 */
class Mart{
    private Seller seller;

    public Mart(Seller seller){
        this.seller = seller;
    }

    public void order(){
        seller.sell();
    }
}

