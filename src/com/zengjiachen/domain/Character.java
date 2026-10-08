package com.zengjiachen.domain;

public class Character {
    public String name;
    public int HP;
    public int maxHp;
    public int attack;
    public int defense;
    public Character(){

    }
    public Character(String name, int HP, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        maxHp = HP;
        this.attack = attack;
        this.defense = defense;
    }
//    1判断存活状态
    public boolean isAlive() {
        return HP > 0;
    }
//    2恢复生命值
    public void heal(int amount)
    {
        HP+=amount;
        if(HP > maxHp)
            HP = maxHp;
    }
//    3受到伤害
    public void takeDamage(int damage){
        HP-=damage;
        if(HP < 0)
            HP = 0;
    }
//    展示人物属性
    public String show() {
        return (name+"[当前生命："+HP+",攻击"+attack+",防御"+defense+"]");

    }
}
