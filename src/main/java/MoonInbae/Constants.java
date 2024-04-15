package MoonInbae;

public class Constants {
    public enum EInputMessages {
        inputPrice("구입금액을 입력해 주세요."),
        purchaseNum("개를 구매했습니다."),
        inputWinningNum("당첨 번호를 입력해 주세요."),
        inputBonusNum("보너스 번호를 입력해 주세요."),
        resultTitle("당첨 통계\n---"),
        result3Num("3개 일치 (5,000원) - "),
        result4Num("4개 일치 (50,000원) - "),
        result5Num("5개 일치 (1,500,000원) - "),
        result6BonusNum("5개 일치, 보너스 볼 일치 (30,000,000원) - "),
        result6Num("6개 일치 (2,000,000,000원) - "),
        resultRate("총 수익률은 ");
        private String text;
        private EInputMessages(String text) {
            this.text = text;
        }
        public String getText() {
            return this.text;
        }
        public int getInt() {
            return Integer.parseInt(text);
        }
    }

    public enum EWinningMoney {
        first("2000000000"),
        second("30000000"),
        third("1500000"),
        fourth("50000"),
        fifth("5000");
        private String text;
        private EWinningMoney(String text) {
            this.text = text;
        }
        public int getInt() {
            return Integer.parseInt(text);
        }
    }

    public enum EErrorMessage {
        EInputMoneyError("금액은 1000원 단위로 기입해주세요."),
        EInputWinningNumFormatError("당첨 번호는 6개로 기입해주세요."),
        EInputWrongWinningNumError("당첨 번호는 1~45의 숫자로 기입해주세요."),
        EInputDuplicatedWinningNumError("당첨 번호는 중복되지 않는 숫자로 기입해주세요."),
        EInputWrongBonusNumError("보너스 번호는 1~45의 숫자로 기입해주세요."),
        EInputDuplicatedBonusNumError("보너스 번호는 당첨 번호롸 중복되지 않는 숫자로 기입해주세요.");
        private String text;
        private EErrorMessage(String text) {
            this.text = text;
        }
        public String getText() {
            return this.text;
        }
    }

}
