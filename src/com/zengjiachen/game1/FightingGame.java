package com.zengjiachen.game1;
import java.sql.SQLOutput;
import java.util.Scanner;
import com.zengjiachen.*;
import com.zengjiachen.domain.HeroCharacter;
import com.zengjiachen.domain.EnemyCharacter;
import java.util.ArrayList;
import java.util.Random;
public class FightingGame {
    public void gamestart(String username) {
        System.out.println("==================");
        System.out.println("欢迎来到文字游戏" + username);
        System.out.println("==================");
        HeroCharacter player = createPlayerCharacter(username);
        System.out.println("角色创建成功！");
        System.out.println("初始属性：" + player.show());
        System.out.println("拥有技能：" + player.showSkills());
        ArrayList<EnemyCharacter> enemyList = new ArrayList<EnemyCharacter>();
        enemyList.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击"));
        enemyList.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "敏捷攻击"));
        enemyList.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态"));
        enemyList.add(new EnemyCharacter("神秘法师", 70, 25, 8, "火球术"));
        int count = 1;
        int win = 0;
        while (player.isAlive()) {
            if (win != 0) {
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c = enemyList.get(i);
                    c.maxHp += 10;
                    c.HP = c.maxHp;
                    c.attack += 3;
                    c.defense += 2;
                    c.defending = false;
                }
            }
            Random R = new Random();
            int index = R.nextInt(enemyList.size());
            EnemyCharacter enemy = enemyList.get(index);
            System.out.println("敌人出现：" + enemy.show());

//            战斗开始----------------------
            System.out.println("-----------------------------------");
            System.out.println("第" + count + "关" + "对手：" + enemy.name);
            int round = 1;
            while (player.isAlive()) {
                System.out.println("-------------------------------");
                System.out.println("第" + round + "回合开始!");
                System.out.println(getHealthBar(player.name, player.HP, player.maxHp));
                System.out.println(getHealthBar(enemy.name, enemy.HP, enemy.maxHp));
                System.out.println("1.普通攻击,2.强力一击,3.生命汲取");
                playerTurn(player, enemy);
//               判断敌方是否被击败（判断敌方的生命值是否小于等于0）
                if (!enemy.isAlive()) {
                    System.out.println("恭喜你击败了" + enemy.name);
                    win++;
                    break;
                }
                enemyTurn(enemy, player);
                if (!player.isAlive()) {
                    System.out.println("你被击败了，游戏结束！");
                    break;
                }
                round++;


            }
            if(player.isAlive())
            {
                int healHP = R.nextInt(21)+20;
                player.heal(healHP);
//              战斗结束！你回复了20-40点生命值
                System.out.println("战斗结束！你回复了" + healHP + "点生命值！");
                System.out.println("当前胜场：" + win);
            }
            if(player.isAlive() && win%3==0&&win>0){
                System.out.println("恭喜你属性得到提升");
                player.maxHp+=30;
                player.attack+=5;
                player.defense+=3;
                player.HP+=30;
                System.out.println("最大生命值提升30，攻击力提升5，防御力提升3!");
                System.out.println("---------------------------------------");
                System.out.println("当前属性：" + player.show());

            }
//            询问玩家是否继续
            if(player.isAlive())
            {
                System.out.println("是否继续游戏？(y/n)");
                Scanner sc = new Scanner(System.in);
                String choice = sc.next();
                if (choice.equals("n")) {
                    System.out.println("游戏结束，感谢游玩！");
                    break;
                }
                else if(choice.equals("y"))
                {
                    System.out.println("继续游戏！");
                    count++;
                }
                else {
                    System.out.println("没有这个选项！，游戏继续");
                }
            }

        }
//        战斗结算
        System.out.println("游戏结束");
        System.out.println("你的最终胜场为"+win);
        System.out.println("感谢游玩！");
        System.out.println("喵~喵~喵~");
    }
//    敌人的回合

    private void enemyTurn(EnemyCharacter enemy, HeroCharacter player) {
        System.out.println("============="+enemy.name+"的回合==============");
        String action ="普通攻击";
        Random R = new Random();
        int index=R.nextInt(10);
        if(index>=5){
            action=enemy.skill;
        }
        switch (action){
            case "普通攻击":
                System.out.println("敌人采取了普通攻击");
                int damage1 = calculateDamage(enemy.attack, player.defense);
                player.takeDamage(damage1);
                System.out.println("敌人对" + player.name + "使用了普通攻击，造成" + damage1 + "点伤害！");
                break;
            case "猛击":
                System.out.println("敌人采取了猛击");
                int damage2 = calculateDamage((int)(enemy.attack * 1.5), player.defense);
                player.takeDamage(damage2);
                System.out.println("敌人对" + player.name + "使用了猛击，造成" + damage2 + "点伤害！");
                break;
            case "敏捷攻击":
                System.out.println("敌人采取了敏捷攻击");
                int damage3=0;
                for (int i = 0; i < 2; i++) {
                    int tmp = calculateDamage((int)(enemy.attack*0.5), player.defense);
                    damage3+=tmp;
                    player.takeDamage(tmp);
                }
                System.out.println("敌人对" + player.name + "使用了敏捷攻击，造成" + damage3 + "点伤害！");
                break;
            case "防御姿态":
                System.out.println(enemy.name + "采取了防御姿态");
                enemy.defending = true;
                break;
            case "火球术":
                System.out.println("敌人采取了火球术");

                int damage5 = calculateDamage((int)(enemy.attack * 1.8), player.defense);
                player.takeDamage(damage5);
                System.out.println("敌人对" + player.name + "使用了火球术，造成" + damage5 + "点伤害！");
                break;
        }
    }

    //    作用：用来创建一个玩家角色
//    参数：用户名
//    放回值(创建好的角色);
    public HeroCharacter createPlayerCharacter(String username) {
        Scanner sc = new Scanner(System.in);
        System.out.println("创建您的角色:");
        System.out.println("您的角色为：" + username);
        int points = 20;
        System.out.println("请分配属性点(共20点):");
        System.out.println("生命值（没点+10HP）");
        System.out.println("攻击力（每点+2ATK）");
        System.out.println("防御力（每点+1DEF）");
//    定义数组把要提示的语句存起来
        String[] attributes = {"生命值", "攻击力", "防御力"};
//    定义数组记录三个属性的分配的属性点
        int[] values = new int[3];
        for (int i = 0; i < attributes.length; i++) {
            System.out.println("分配点数到" + attributes[i] + "(当前剩余" + points + "点)");
            int input = sc.nextInt();
            if (input < 0) {
                System.out.println("无效输入！属性点数不能为负数！");
                input = 0;
            }
            if (input > points) {
                System.out.println("属性点不足！剩余属性点全部分配到" + attributes[i]);
                input = points;
            }
            points = points - input;
            values[i] = input;
        }
        HeroCharacter players = new HeroCharacter(
                username, 100 + values[0] * 10, 10 + values[1] * 2, 0 + values[2]
        );
//        添加玩家的技能
        players.skillList.add("普通攻击");
        players.skillList.add("强力一击");
        players.skillList.add("生命汲取");
        return players;
    }

    //   定义一个方法打印敌我双方的血条
    public String getHealthBar(String name, int Hp, int maxHp) {
        int barLength = 20;
        int filled = (int) ((Hp * 1.0 / maxHp) * barLength);
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": [");
        for (int i = 0; i < barLength; i++) {
            if (i < filled)
                sb.append("-");
            else
                sb.append(" ");
        }
        sb.append("]").append(Hp).append("/").append(maxHp).append("HP");
        return sb.toString();
    }

    public void playerTurn(HeroCharacter player, EnemyCharacter enemy) {
        System.out.println("=========我方回合==========");
        System.out.println("1.普通攻击");
        System.out.println("2.强力一击");
        System.out.println("3.生命汲取");
        System.out.println("选择行动（1-3）：");
        Scanner sc = new Scanner(System.in);
        String choose = sc.next();
        switch (choose) {
            case "1" -> {
                System.out.println("普通攻击");
//                我方的攻击力减去对方的防御力
                int damage = calculateDamage(player.attack, enemy.defense);
                System.out.println("你对" + enemy.name + "使用了普通攻击，造成" + damage + "点伤害！");
                enemy.takeDamage(damage);
            }
            case "2" -> {
                if (player.HP > 10) {
                    System.out.println("强力一击");
//                我方的攻击力的1.5倍减去对方的防御力
                    player.takeDamage(10);
                    int damage = calculateDamage((int) (player.attack * 1.8), enemy.defense);
                    System.out.println("你使用了强力一击，消耗了10点体力！");
                    System.out.println("你对" + enemy.name + "使用了强力一击，造成" + damage + "点伤害！");
                    enemy.takeDamage(damage);
                } else {
                    System.out.println("体力不足，攻击失败");
                }
            }
            case "3" -> {
                System.out.println("生命汲取");
                if(player.HP>10){
                    player.takeDamage(10);
                    Random R = new Random();
                    int heal1 = R.nextInt(21);
                    player.heal(heal1);
                    System.out.println("你使用了生命汲取，恢复了" + heal1 + "点生命值！");
                }
            }
            default -> System.out.println("普通攻击");
        }
    }

    //    作用：用来计算双防战斗的时候，造成的伤害
    public int calculateDamage(int attack, int defense) {
        int damage = attack - defense;
        if (damage < 1) {
            damage = 1;
        }
        return damage;
    }
}
