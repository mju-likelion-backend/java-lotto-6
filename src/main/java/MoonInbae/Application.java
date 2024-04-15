package MoonInbae;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    private float calculateWinningRate(int money, int winningMoney) {
        float rate = Math.round((float)winningMoney/(float)money*100);
        return rate;
    }

    private int calculateWinningMoney(List<Integer> rankCount) {
        int winningMoney = 0;
        for(int i=0; i<rankCount.size(); i++) { // i가 등 수
            winningMoney += Constants.EWinningMoney.values()[i].getInt()*rankCount.get(i);
        }
        return winningMoney;
    }
    private int matchLotto(List<Integer> numbers, List<Integer> nums, int bonus_num) {
        int count = 0;
        for(int i:nums) {
            if(numbers.contains(i))
                count++;
        }

        if(count == 3) { return 4; }
        if(count == 4) { return 3;}
        if(count == 5 && numbers.contains(bonus_num)) { return 1; }
        if(count == 5) { return 2; }
        if(count == 6) { return 0; }

        return -1;
    }
    private List<Integer> separateLotto(List<Lotto> lottos, List<Integer> nums, int bonusNum) {
        List<Integer> rankCount = new ArrayList<Integer>(); // 몇 등이 몇 개 있는지 확인 용도
        rankCount.add(0);
        rankCount.add(0);
        rankCount.add(0);
        rankCount.add(0);
        rankCount.add(0);
        for(Lotto lotto:lottos) {
            List<Integer> numbers = lotto.getNumbers();
            int winningRank = this.matchLotto(numbers, nums, bonusNum);
            if(winningRank != -1)
                rankCount.set(winningRank,rankCount.get(winningRank)+1);
        }
        return rankCount;
    }
    private List<Integer> getWinningNum(String numbers) {
        List<Integer> nums = new ArrayList<Integer>();
        String[] str_nums = numbers.split(",");
        for(String s : str_nums)
            nums.add(Integer.parseInt(s));
        return nums;
    }

    private Lotto makeLotto() {
        List<Integer> lotto_nums = new ArrayList<Integer>();
        for(int m=0; m<6; m++) {
            int i = (int) (Math.random() * 45 + 1);
            if (lotto_nums.contains(i)) {
                m--;
                continue;
            }
            lotto_nums.add(i);
        }
        Lotto lotto = new Lotto(lotto_nums);
        lotto.sort();
        return lotto;
    }
    private void printResult(List<Integer> rankCount, int money) {
        System.out.println(Constants.EInputMessages.resultTitle.getText());
        System.out.println(Constants.EInputMessages.result3Num.getText()+rankCount.get(4)+"개");
        System.out.println(Constants.EInputMessages.result4Num.getText()+rankCount.get(3)+"개");
        System.out.println(Constants.EInputMessages.result5Num.getText()+rankCount.get(2)+"개");
        System.out.println(Constants.EInputMessages.result6BonusNum.getText()+rankCount.get(1)+"개");
        System.out.println(Constants.EInputMessages.result6Num.getText()+rankCount.get(0)+"개");
        int winningMoney = this.calculateWinningMoney(rankCount);
        float winningRate = this.calculateWinningRate(money, winningMoney);
        System.out.println(Constants.EInputMessages.resultRate.getText()+winningRate+"%입니다.");
    }
    private List<Lotto> makeLottos(int times) {
        List<Lotto> lottos = new ArrayList<Lotto>();
        for(int n=0; n<times; n++) lottos.add(this.makeLotto());
        for(Lotto lotto : lottos) System.out.println(lotto.getNumbers());
        System.out.println();
        return lottos;
    }

    private int checkMoney(int money) {
        if(money % 1000 != 0) {
            throw new IllegalArgumentException(Constants.EErrorMessage.EInputMoneyError.getText());
        }
        return money;
    }

    private int inputMoney(Scanner sc) {
        int money;
        while (true) {
            System.out.println(Constants.EInputMessages.inputPrice.getText());
            money = sc.nextInt(); // 금액
            try {
                this.checkMoney(money);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        System.out.println();
        return money;
    }

    private List<Integer> checkWinningNunmbers(List<Integer> nums) {
        if (nums.size() != 6) {
            throw new IllegalArgumentException(Constants.EErrorMessage.EInputWinningNumFormatError.getText());
        }
        List<Integer> checkNumsList = new ArrayList<Integer>();
        for(int num : nums) {
            if(num < 1 || num > 45) {
                throw new IllegalArgumentException(Constants.EErrorMessage.EInputWrongWinningNumError.getText());
            }
            if(checkNumsList.contains(num)) {
                throw new IllegalArgumentException(Constants.EErrorMessage.EInputDuplicatedWinningNumError.getText());
            }
            checkNumsList.add(num);
        }
        return nums;
    }

    private List<Integer> inputWinningNumbers(Scanner sc) {
        System.out.println(Constants.EInputMessages.inputWinningNum.getText());
        String numbers = sc.next(); // 당첨 번호
        System.out.println();
        return this.getWinningNum(numbers);
    }

    private List<Integer> getWinningNumbers(Scanner sc) {
        List<Integer> nums = null;
        while (true) {
            nums = inputWinningNumbers(sc);
            try {
                this.checkWinningNunmbers(nums);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        return nums;
    }

    private int checkBonusNum(int bonusNum, List<Integer> nums) {
        if(bonusNum < 1 || bonusNum > 45) {
            throw new IllegalArgumentException(Constants.EErrorMessage.EInputWrongBonusNumError.getText());
        }
        if(nums.contains(bonusNum)) {
            throw new IllegalArgumentException(Constants.EErrorMessage.EInputDuplicatedBonusNumError.getText());
        }
        return bonusNum;
    }

    private int inputBonusNum(Scanner sc, List<Integer> nums) {
        int bonusNum;
        while (true) {
            System.out.println(Constants.EInputMessages.inputBonusNum.getText());
            bonusNum = sc.nextInt(); // 보너스 번호
            try {
                this.checkBonusNum(bonusNum, nums);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        System.out.println();
        return bonusNum;
    }

    private void start(Scanner sc) {
        int money = inputMoney(sc);
        int times = money/1000; // 로또 가능 횟수
        System.out.println(times + Constants.EInputMessages.purchaseNum.getText());
        List<Lotto> lottos = this.makeLottos(times);
        List<Integer> nums = this.getWinningNumbers(sc);
        int bonusNum = this.inputBonusNum(sc, nums);
        List<Integer> rankCount = this.separateLotto(lottos, nums, bonusNum);
        this.printResult(rankCount, money);
    }

    public static void main(String[] args) {
        // TODO: 프로그램 구현
        Application application = new Application();
        Scanner sc = new Scanner(System.in);
        application.start(sc);
        sc.close();
    }
}
