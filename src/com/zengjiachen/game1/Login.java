package com.zengjiachen.game1;
import com.zengjiachen.domain.User;
import com.zengjiachen.game1.FightingGame;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
public class Login {
    Scanner sc = new Scanner(System.in);
    public Login(){

    }
    public void start(){
        ArrayList<User> List = new ArrayList<>();
        while (true) {
            System.out.println("==================");
            System.out.println("欢迎来到文字游戏");
            System.out.println("==================");
            System.out.println("请选择你的操作1:登录2：注册3：退出");
            String choose = sc.next();

            switch (choose) {
                case "1" -> login(List);
                case "2" -> register(List);
                case "3" -> {
                    System.out.println("用户选择了退出操作");
                    System.exit(0);

                }
                default -> System.out.println("输入有误，请重新选择");

            }
        }

    }
    public void login(ArrayList<User> List){
        System.out.println("用户选择了登录操作~");
        System.out.println("请输入用户名");
        String username=sc.next();
        if(!containsUsername(List,username))
        {
            System.out.println("用户名不存在，请重新输入");
            return;
//            如果用户名不存在，先回去注册
        }
        int index = findIndex(List, username);
        User u = List.get(index);
        if(!u.isStatus())
        {
            System.out.println("用户已禁用，请联系管理员131313442");
            return;
        }
        String rightPassword =u.getPassword();
        int count=3;
        for(int i=0;i<3;i++)
        {
            System.out.println("请输入密码");
            String password=sc.next();
//        验证码是否正确
            while(true)
            {
                String rightCode=getCode();
                System.out.println("验证码是："+rightCode);
                System.out.println("请输入验证码");
                String inputCode=sc.next();
                if(!inputCode.equalsIgnoreCase(rightCode))
                {
                    System.out.println("验证码错误");
                    continue;
                }
                else
                {
                    System.out.println("验证码正确");
                    break;
                }
            }
            if(rightPassword.equals(password))
            {
                System.out.println("登录成功，游戏启动");
                FightingGame fightingGame = new FightingGame();
                fightingGame.gamestart(u.getUsername());
                break;
            }
            else{
                System.out.println("密码错误，请重新输入");
            }
            if(i==2) {
                System.out.println("您还有" + (3 - i - 1) + "次机会");
            }
            else
            {
                System.out.println("您输入密码错误次数过多，账号已禁用");
                u.setStatus(false);
                System.out.println("用户已禁用，请联系管理员131313442");
                return;

            }

        }
    }

    public int findIndex(ArrayList<User> List,String username){
        for(int i=0;i<List.size();i++)
        {
            if(List.get(i).getUsername().equals(username))
            {
                return i;
            }
        }
        return -1;
    }

    public void register(ArrayList<User> List){
        System.out.println("用户选择了注册操作~");
        String username= null;
        while (true) {
            System.out.println("请输入用户名");
            username = sc.next();
            if(!checkLen(3,16,username))
            {
                System.out.println("用户名长度不符合要求，请重新输入,必须在3~16为之间");
                continue;
            }
            if(!checkUsername(username))
            {
                System.out.println("用户名不符合要求");
                continue;
            }
            if(containsUsername(List,username))
            {
                System.out.println("用户名已存在，请重新输入");
                continue;
            }
            break;
        }

//        当代码进入到这里用户的账号没有问题
        System.out.println("请输入密码");
        String password1 = null;
        String password2=null;
        while (true) {
            password1=sc.next();
            if(!checkLen(3,8,password1))
            {
                System.out.println("密码长度不符合要求，请重新输入,必须在3~8为之间");
                continue;
            }
            if(!checkPassword(password1))
            {
                System.out.println("密码只能是数字加字母");
                continue;
            }
            System.out.println("请再次输入密码");
            password2=sc.next();
            if(!checkPassword(password1,password2))
            {
                System.out.println("两次输入的密码不一致，请重新输入");
                continue;
            }
            break;
        }
        User user = new User(username, password1);
        List.add(user);
        System.out.println("注册成功");
    }
    public int[] getCount(String userInfo){
        int charCount=0;
        int numCount=0;
        int otherCount=0;
        for(int i=0;i<userInfo.length();i++){
            char c =userInfo.charAt(i);
            if(c>='a'&&c<='z')
            {
                charCount++;
            }
            else if(c>='0'&&c<='9'){
                numCount++;
            }
            else {
                otherCount++;
            }
        }
        return new int[]{charCount,numCount,otherCount};
    }
    public boolean checkLen(int minLen,int maxLen,String str){
        return str.length()>minLen&&str.length()<=maxLen;

    }
    public boolean checkUsername(String str)
    {
//        1:字母的个数
//        2:数字的个数
//        3:其他字母的个数
        int[] count = getCount(str);
        return count[0]>=1&&count[1]>=0&&count[2]==0;
    }
    public boolean checkPassword(String password)
    {
        int[] count=getCount(password);
        return count[0]>=1&&count[1]>=1&&count[2]==0;
    }
    public boolean containsUsername(ArrayList<User> List,String username)
    {
        for(User u:List)
        {
            if(u.getUsername().equals(username))
            {
                return true;
            }
        }
        return false;
    }
    public boolean checkPassword(String password1,String password2)
    {
        if(password1.equals(password2))
        {
            return true;
        }
        return false;
    }
    public static String getCode() {
        ArrayList<Character> list1 = new ArrayList<>();
        StringBuilder SB = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 26; i++) {
            list1.add((char) ('A' + i));
            list1.add((char) ('a' + i));
        }
        for(int i=0;i<5;i++)
        {
            int dex=random.nextInt(list1.size());
            char ch = list1.get(dex);
            SB.append(ch);
        }
        int num=random.nextInt(10);
        int pp=random.nextInt(5);
        SB.replace(pp,pp+1,String.valueOf(num));
        return SB.toString();
    }
}

