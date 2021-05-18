package it.polimi.ingsw.clientmodel;

import it.polimi.ingsw.CardDecoder.CardDecoder;
import it.polimi.ingsw.MyObservable;
import it.polimi.ingsw.model.cards.Card;

public class ClientModelDevCardSpace extends MyObservable {
    int codeBlue1;
    int codeBlue2;
    int codeBlue3;
    int codeGreen1;
    int codeGreen2;
    int codeGreen3;
    int codePurple1;
    int codePurple2;
    int codePurple3;
    int codeYellow1;
    int codeYellow2;
    int codeYellow3;

    public ClientModelDevCardSpace() {
        this.codeBlue1 = 0;
        this.codeBlue2 = 0;
        this.codeBlue3 = 0;
        this.codeGreen1 = 0;
        this.codeGreen2 = 0;
        this.codeGreen3 = 0;
        this.codePurple1 = 0;
        this.codePurple2 = 0;
        this.codePurple3 = 0;
        this.codeYellow1 = 0;
        this.codeYellow2 = 0;
        this.codeYellow3 = 0;
    }

    public int getCodeBlue1() {
        return codeBlue1;
    }

    public int getCodeBlue2() {
        return codeBlue2;
    }

    public int getCodeBlue3() {
        return codeBlue3;
    }

    public int getCodeGreen1() {
        return codeGreen1;
    }

    public int getCodeGreen2() {
        return codeGreen2;
    }

    public int getCodeGreen3() {
        return codeGreen3;
    }

    public int getCodePurple1() {
        return codePurple1;
    }

    public int getCodePurple2() {
        return codePurple2;
    }

    public int getCodePurple3() {
        return codePurple3;
    }

    public int getCodeYellow1() {
        return codeYellow1;
    }

    public int getCodeYellow2() {
        return codeYellow2;
    }

    public int getCodeYellow3() {
        return codeYellow3;
    }

  public void setDevCardSpaceUpdate(int codeBlue1, int codeBlue2, int codeBlue3, int codeGreen1, int codeGreen2,
                                 int codeGreen3, int codePurple1, int codePurple2, int codePurple3,
                                 int codeYellow1, int codeYellow2, int codeYellow3){

      this.codeBlue1 = codeBlue1;
      this.codeBlue2 = codeBlue2;
      this.codeBlue3 = codeBlue3;
      this.codeGreen1 = codeGreen1;
      this.codeGreen2 = codeGreen2;
      this.codeGreen3 = codeGreen3;
      this.codePurple1 = codePurple1;
      this.codePurple2 = codePurple2;
      this.codePurple3 = codePurple3;
      this.codeYellow1 = codeYellow1;
      this.codeYellow2 = codeYellow2;
      this.codeYellow3 = codeYellow3;
      notifyObservers();
  }

  public void visualizeDevelopmentCardsSpace(){
      CardDecoder cardDecoder = new CardDecoder();

      if(this.codeGreen3 == 0 && this.codeBlue3==0 && this.codeYellow3 == 0 && this.codePurple3 == 0 && this.codeGreen2 == 0 &&
              this.codeBlue2 == 0 && this.codeYellow2 == 0 && this.codePurple2 == 0 &&
              this.codeGreen1 == 0 && this.codeBlue1 == 0 && this.codeYellow1 == 0 && this.codePurple1 == 0){
          return;
      }





      cardDecoder.matrixFourCardsContainer(this.codeGreen3,this.codeBlue3, this.codeYellow3, this.codePurple3);
      cardDecoder.matrixFourCardsContainer(this.codeGreen2,this.codeBlue2, this.codeYellow2, this.codePurple2);
      cardDecoder.matrixFourCardsContainer(this.codeGreen1,this.codeBlue1, this.codeYellow1, this.codePurple1);

  }


}
