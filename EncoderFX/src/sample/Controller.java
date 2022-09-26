package sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.fxml.FXMLLoader;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.scene.control.Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL;


public class Controller {


    public boolean cycled = false;               //Indicates if the program went througt reflectorr

    //Rotors' global vars:
    public int rotorQ = 0;                       //Rotor's number (rotorQueue)
    public int numbers[] = new int[3];           //Which rotors are placed in machine
    public int rotorsPositions[] = new int[3];   //What positions are rotors set on
    public int RSP[] = new int[3];

    //Reflector's global vars:
    public int reflectorType = 0;                   //Type of Reflector placed in machine

    //Commutator's global vars:                         //Commutator settings
    public char commutatorABC[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public String string = "";
    public String string2 = "";
    public boolean wordMode = false;
    public boolean fileMode = false;
    private Desktop desktop = Desktop.getDesktop();
    public boolean changed[] = new boolean[26];
    public boolean comChanged = false;


    public void setCommutator(char a, char b){
        if (commutatorABC[(int)(a - 'A')]-'A' < 26 && commutatorABC[(int)(b-'A')]-'A' < 26){
            commutatorABC[(int)(a - 'A')] = b;
            commutatorABC[(int)(b - 'A')] = a;
        }
        else if((commutatorABC[(int)(a - 'a')] - commutatorABC[(int)(b - 'a')]) == (int)(b - a)){
            commutatorABC[(int)(a - 'A')] = (char)(b-32);
            commutatorABC[(int)(b - 'A')] = (char)(a-32);
        }
        else if(commutatorABC[(int)(a - 'A')] >=26 && commutatorABC[(int)(b-'A')] >=26){
            System.out.println("Can't change symbols, which are already changed!");
        }
    }

    public void clearCommutator() {
        for (int i = 0; i<26; i++)
            commutatorABC[i]=(char)('A'+i);
    }
    public String getCommSymbols(char c){
        if ((int)commutatorABC[c - 'A'] > 25)
            return String.valueOf(commutatorABC[c-'A']-32);
        else
            return String.valueOf(commutatorABC[c-'A']);
    }
    public void commutator(char c){
        char cout = commutatorABC[(int)(c - 'A')];
        if ((int)(cout - 'A') >= 26)
            cout = (char)(cout-32);
        if (cycled == false)
            rotor(cout);
        else{
            cycled = false;
            string2 += cout;
            this.output.setText(string2);
            //Окончательный вывод на лампочки
        }
    }

    public void rotor(char c){
        //String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (cycled == false && rotorQ == 0) {
            if (rotorsPositions[0] == 25) {
                rotorsPositions[0] = 0;
                if (rotorsPositions[1] == 25) {
                    rotorsPositions[1] = 0;
                    if (rotorsPositions[2] == 25)
                        rotorsPositions[2] = 0;
                    else
                        rotorsPositions[2]++;
                }
                else
                    rotorsPositions[1]++;
            }
            else
                rotorsPositions[0]++;
        }
        //System.out.println(rotorsPositions[rotorQ]);
        int charCode = (int)(c - 'A' + rotorsPositions[rotorQ]) % 26;
        String str = "";

        if (cycled == false) {
            switch (numbers[rotorQ]) {
                case 1:
                    str += "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
                    break;
                case 2:
                    str += "AJDKSIRUXBLHWTMCQGZNPYFVOE";
                    break;
                case 3:
                    str += "BDFHJLCPRTXVZNYEIWGAKMUSQO";
                    break;
                case 4:
                    str += "ESOVPZJAYQUIRHXLNFTGKDCMWB";
                    break;
                case 5:
                    str += "VZBRGITYUPSDNHLXAWMJQOFECK";
                    break;
            }
            char c2 = str.charAt(charCode);
            if (rotorQ == 2){
                //System.out.printf("Rotor:%d\tresult:%c\n", rotorQ+1, c2);
                c2=(char)(c2-rotorsPositions[rotorQ]);
                //System.out.printf("Rotor:%d\tresult:%c\n", rotorQ+1, c2);
                if (c2>'Z')
                    c2-=26;
                else if (c2<'A')
                    c2+=26;
                reflector(c2);
            }
            else {
                //System.out.printf("Rotor:%d\tresult:%c\n", rotorQ+1, c2);
                c2=(char)(c2-rotorsPositions[rotorQ]);
                if (c2>'Z')
                    c2-=26;
                else if (c2<'A')
                    c2+=26;
                rotorQ++;
                rotor(c2);
            }
        }
        else {
            switch (numbers[rotorQ]) {
                case 1:
                    //str  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    str += "UWYGADFPVZBECKMTHXSLRINQOJ";
                    break;
                case 2:
                    str += "AJPCZWRLFBDKOTYUQGENHXMIVS";
                    break;
                case 3:
                    str += "TAGBPCSDQEUFVNZHYIXJWLRKOM";
                    break;
                case 4:
                    str += "HZWVARTNLGUPXQCEJMBSKDYOIF";
                    break;
                case 5:
                    str += "QCYLXWENFTZOSMVJUDKGIARPHB";
                    break;
            }
            char c2 = str.charAt(charCode);
            if (rotorQ == 0){
                //System.out.printf("Rotor:%d\tresult:%c\n", rotorQ+1, c2);
                c2=(char)(c2-rotorsPositions[rotorQ]);
                if (c2>'Z')
                    c2-=26;
                else if (c2<'A')
                    c2+=26;
                //System.out.printf("Rotor:%d\tresult:%c\n", rotorQ+1, c2);
                commutator(c2);
                //System.out.print(c2);
            }
            else {
                //System.out.printf("Rotor:%d\tresult:%c\n", rotorQ+1, c2);
                c2=(char)(c2-rotorsPositions[rotorQ]);
                if (c2>'Z')
                    c2-=26;
                else if (c2<'A')
                    c2+=26;
                rotorQ--;
                rotor(c2);
            }

        }
    }

    public void reflector(char c) {
        cycled = true;
        int neC = 0;
        String str1 = "";
        String str2 = "";
        switch (reflectorType) {
            case 1:
                str1 += "ABCDEFGIJKMTV";
                str2 += "YRUHQSLPXNOZW";
                switch (c) {
                    case 'A':case 'B':case 'C':case 'D':case 'E':case 'F':case 'G':case 'I':case 'J':case 'K':case 'M':case 'T':case 'V':
                        for (int j = 0; j < str1.length(); j++) {
                            if (c == str1.charAt(j)) {
                                neC = j;
                            }
                        }
                        c = str2.charAt(neC);
                        //System.out.println(c);
                        //rotor(c);
                        break;
                    case 'Y':case 'R':case 'U':case 'H':case 'Q':case 'S':case 'L':case 'P':case 'X':case 'N':case 'O':case 'Z':case 'W':
                        for (int j = 0; j < str2.length(); j++) {
                            if (c == str2.charAt(j)) {
                                neC = j;
                            }
                        }
                        c = str1.charAt(neC);
                        //System.out.println(c);
                        //rotor(c);
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                str1 += "ABCDEGHKLMNTS";
                str2 += "FVPJIOYRZXWQU";
                switch (c) {
                    case 'A': case 'B': case 'C': case 'D': case 'E': case 'G': case 'H': case 'K': case 'L': case 'M': case 'N': case 'T': case 'S':
                        for (int j = 0; j < str1.length(); j++) {
                            if (c == str1.charAt(j)) {
                                neC = j;
                            }
                        }
                        c = str2.charAt(neC);
                        //System.out.println(c);
                        //rotor(c);
                        break;
                    case 'F': case 'V': case 'P': case 'J': case 'I': case 'O': case 'Y': case 'R': case 'Z': case 'X': case 'W': case 'Q': case 'U':
                        for (int j = 0; j < str2.length(); j++) {
                            if (c == str2.charAt(j)) {
                                neC = j;
                            }
                        }
                        c = str1.charAt(neC);
                        //System.out.println(c);
                        //rotor(c);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        rotor(c);
    }



    public void main(){
        numbers[0] = (int) this.rotorType1.getValue();
        numbers[1] = (int) this.rotorType2.getValue();
        numbers[2] = (int) this.rotorType3.getValue();
        rotorsPositions[0] =  (int) this.RotPos1.getValue()-1;
        rotorsPositions[1] =  (int) this.RotPos2.getValue()-1;
        rotorsPositions[2] =  (int) this.RotPos3.getValue()-1;
        reflectorType = (int) this.reflector.getValue();
        //String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //setCommutator('A', 'B');
        //setCommutator('Y', 'C');
        for (int i = 0; i<string.length(); i++){
            commutator(string.charAt(i));
        }
        System.out.println("");
    }


    @FXML private Label input;
    @FXML private Label output;
    @FXML private Spinner rotorType1;
    @FXML private Spinner rotorType2;
    @FXML private Spinner rotorType3;
    @FXML private Spinner RotPos1;
    @FXML private Spinner RotPos2;
    @FXML private Spinner RotPos3;
    @FXML private Spinner reflector;
    @FXML private MenuItem OpenFileMode;
    @FXML private MenuItem savingFile;
    private TextField cA;
    private TextField cB;
    private TextField cC;
    private TextField cD;
    private TextField cE;
    private TextField cF;
    private TextField cG;
    private TextField cH;
    private TextField cI;
    private TextField cJ;
    private TextField cK;
    private TextField cL;
    private TextField cM;
    private TextField cN;
    private TextField cO;
    private TextField cP;
    private TextField cQ;
    private TextField cR;
    private TextField cS;
    private TextField cT;
    private TextField cU;
    private TextField cV;
    private TextField cW;
    private TextField cX;
    private TextField cY;
    private TextField cZ;
    public String string3 = "";




    public void OpeningFile(ActionEvent q){
        string="";
        string2="";
        fileMode = true;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(input.getScene().getWindow());
        /*if (file != null) {
            openFile(file);
        }*/
        try{
            Scanner in = new Scanner(file);
            while(in.hasNextLine())
                string += in.nextLine();
            main();
            input.setText(string);
        }
        catch(FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        }
    }
    public void SavingFile(ActionEvent q) {
        FileChooser saving = new FileChooser();
        saving.setTitle("Save File");
        saving.setInitialFileName("Encoded");
        saving.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Text File", "*.txt"));
        try{
            File file = saving.showSaveDialog(input.getScene().getWindow());
            saving.setInitialDirectory(file.getParentFile());
            if (file != null) {
                saveTextToFile(string2, file);
            }
        }
        catch (Exception ex){

        }

        /*
        File file = saving.showSaveDialog(input.getScene().getWindow());
        if (file != null) {
            try {
                .write(SwingFXUtils.fromFXImage(pic.getImage(),
                        null), "txt", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }*/
    }
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void VRUMVRUM(ActionEvent q) {
        if (wordMode) {
            RSP[0] = (int) (RotPos1.getValue()) - 1;
            RSP[1] = (int) (RotPos2.getValue()) - 1;
            RSP[2] = (int) (RotPos3.getValue()) - 1;
            main();
            //RotPos1.getValueFactory().setValue(rotorsPositions[0] - (rotorsPositions[0] - 1));
            //RotPos2.getValueFactory().setValue(rotorsPositions[1] - (rotorsPositions[1] - 1));
            //RotPos3.getValueFactory().setValue(rotorsPositions[2] - (rotorsPositions[2] - 1));
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
        }
    }


    public void setCommSymbols(ActionEvent q){
        setCommutator('A', cA.getCharacters().charAt(0));
        cA.setText(getCommSymbols(cA.getText().charAt(0)));
        setCommutator('B', cB.getCharacters().charAt(0));
        cB.setText(getCommSymbols(cB.getCharacters().charAt(0)));
        setCommutator('C', cC.getCharacters().charAt(0));
        cC.setText(getCommSymbols(cC.getCharacters().charAt(0)));
        setCommutator('D', cD.getCharacters().charAt(0));
        cD.setText(getCommSymbols(cD.getCharacters().charAt(0)));
        setCommutator('E', cE.getCharacters().charAt(0));
        cE.setText(getCommSymbols(cE.getCharacters().charAt(0)));
        setCommutator('F', cF.getCharacters().charAt(0));
        cF.setText(getCommSymbols(cF.getCharacters().charAt(0)));
        setCommutator('G', cG.getCharacters().charAt(0));
        cG.setText(getCommSymbols(cG.getCharacters().charAt(0)));
        setCommutator('H', cH.getCharacters().charAt(0));
        cH.setText(getCommSymbols(cH.getCharacters().charAt(0)));
        setCommutator('I', cI.getCharacters().charAt(0));
        cI.setText(getCommSymbols(cI.getCharacters().charAt(0)));
        setCommutator('J', cJ.getCharacters().charAt(0));
        cJ.setText(getCommSymbols(cJ.getCharacters().charAt(0)));
        setCommutator('K', cK.getCharacters().charAt(0));
        cK.setText(getCommSymbols(cK.getCharacters().charAt(0)));
        setCommutator('L', cL.getCharacters().charAt(0));
        cL.setText(getCommSymbols(cL.getCharacters().charAt(0)));
        setCommutator('M', cM.getCharacters().charAt(0));
        cM.setText(getCommSymbols(cM.getCharacters().charAt(0)));
        setCommutator('N', cN.getCharacters().charAt(0));
        cN.setText(getCommSymbols(cN.getCharacters().charAt(0)));
        setCommutator('O', cO.getCharacters().charAt(0));
        cO.setText(getCommSymbols(cO.getCharacters().charAt(0)));
        setCommutator('P', cP.getCharacters().charAt(0));
        cP.setText(getCommSymbols(cP.getCharacters().charAt(0)));
        setCommutator('Q', cQ.getCharacters().charAt(0));
        cQ.setText(getCommSymbols(cQ.getCharacters().charAt(0)));
        setCommutator('R', cR.getCharacters().charAt(0));
        cR.setText(getCommSymbols(cR.getCharacters().charAt(0)));
        setCommutator('S', cS.getCharacters().charAt(0));
        cS.setText(getCommSymbols(cS.getCharacters().charAt(0)));
        setCommutator('T', cT.getCharacters().charAt(0));
        cT.setText(getCommSymbols(cT.getCharacters().charAt(0)));
        setCommutator('U', cU.getCharacters().charAt(0));
        cU.setText(getCommSymbols(cU.getCharacters().charAt(0)));
        setCommutator('V', cV.getCharacters().charAt(0));
        cV.setText(getCommSymbols(cV.getCharacters().charAt(0)));
        setCommutator('W', cW.getCharacters().charAt(0));
        cW.setText(getCommSymbols(cW.getCharacters().charAt(0)));
        setCommutator('X', cX.getCharacters().charAt(0));
        cX.setText(getCommSymbols(cX.getCharacters().charAt(0)));
        setCommutator('Y', cY.getCharacters().charAt(0));
        cY.setText(getCommSymbols(cY.getCharacters().charAt(0)));
        setCommutator('Z', cZ.getCharacters().charAt(0));
        cZ.setText(getCommSymbols(cZ.getCharacters().charAt(0)));

        //System.out.println("A");
        /*if (!changed[0]) {
            switch (cA.getText().charAt(0)) {
                case 'A':
                    cA.setText("A");
                    break;
                case 'B':
                    cB.setText("A");
                    break;
                case 'C':
                    cC.setText("A");
                    break;
                case 'D':
                    cD.setText("A");
                    break;
                case 'E':
                    cE.setText("A");
                    break;
                case 'F':
                    cF.setText("A");
                    break;
                case 'G':
                    cG.setText("A");
                    break;
                case 'H':
                    cH.setText("A");
                    break;
                case 'I':
                    cI.setText("A");
                    break;
                case 'J':
                    cJ.setText("A");
                    break;
                case 'K':
                    cK.setText("A");
                    break;
                case 'L':
                    cL.setText("A");
                    break;
                case 'M':
                    cM.setText("A");
                    break;
                case 'N':
                    cN.setText("A");
                    break;
                case 'O':
                    cO.setText("A");
                    break;
                case 'P':
                    cP.setText("A");
                    break;
                case 'Q':
                    cQ.setText("A");
                    break;
                case 'R':
                    cR.setText("A");
                    break;
                case 'S':
                    cS.setText("A");
                    break;
                case 'T':
                    cT.setText("A");
                    break;
                case 'U':
                    cU.setText("A");
                    break;
                case 'V':
                    cV.setText("A");
                    break;
                case 'W':
                    cW.setText("A");
                    break;
                case 'X':
                    cX.setText("A");
                    break;
                case 'Y':
                    cY.setText("A");
                    break;
                case 'Z':
                    cZ.setText("A");
                    break;
            }
        }*/
    }
    public void presQ(ActionEvent q) {
        if (wordMode) {

            string += "Q";
            this.input.setText(string);
        } else {
            string = "Q";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);
        }
    }
    public void presW(ActionEvent q) {
        if (wordMode) {
            string += "W";
            this.input.setText(string);
        } else {
            string = "W";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presE(ActionEvent q) {
        if (wordMode) {
            string += "E";
            this.input.setText(string);
        }else {
            string = "E";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presR(ActionEvent q) {
        if (wordMode) {
            string += "R";
            this.input.setText(string);
        } else {
            string = "R";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presT(ActionEvent q) {
        if (wordMode) {
            string += "T";
            this.input.setText(string);
        } else {
            string = "T";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presY(ActionEvent q) {
        if (wordMode) {
            string += "Y";
            this.input.setText(string);
        } else {
            string = "Y";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presU(ActionEvent q) {
        if (wordMode) {
            string += "U";
            this.input.setText(string);
        } else {
            string = "U";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presI(ActionEvent q) {
        if (wordMode) {
            string += "I";
            this.input.setText(string);
        } else {
            string = "I";
            string2 = "";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presO(ActionEvent q) {
        if (wordMode) {
            string += "O";
            this.input.setText(string);
        } else {
            string = "O";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presP(ActionEvent q) {
        if (wordMode) {
            string += "P";
            this.input.setText(string);
        } else {
            string = "P";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presA(ActionEvent q) {
        if (wordMode) {
            string += "A";
            this.input.setText(string);
        } else {
            string = "A";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presS(ActionEvent q) {
        if (wordMode) {
            string += "S";
            this.input.setText(string);
        } else {
            string = "S";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presD(ActionEvent q) {
        if (wordMode) {
            string += "D";
            this.input.setText(string);
        } else {
            string = "D";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presF(ActionEvent q) {
        if (wordMode) {
            string += "F";
            this.input.setText(string);
        }else {
            string = "F";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presG(ActionEvent q) {
        if (wordMode) {
            string += "G";
            this.input.setText(string);
        } else {
            string = "G";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presH(ActionEvent q) {
        if (wordMode) {

            string += "H";
            this.input.setText(string);
        } else {
            string = "H";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presJ(ActionEvent q) {
        if (wordMode) {
            string += "J";
            this.input.setText(string);
        } else {
            string = "J";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presK(ActionEvent q) {
        if (wordMode) {
            string += "K";
            this.input.setText(string);
        } else {
            string = "K";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presL(ActionEvent q) {
        if (wordMode) {
            string += "L";
            this.input.setText(string);
        } else {
            string = "L";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presZ(ActionEvent q) {
        if (wordMode) {
            string += "Z";
            this.input.setText(string);
        } else {
            string = "Z";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presX(ActionEvent q) {
        if (wordMode) {
            string += "X";
            this.input.setText(string);
        }else {
            string = "X";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presC(ActionEvent q) {
        if (wordMode) {
            string += "C";
            this.input.setText(string);
        }else{
            string = "C";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presV(ActionEvent q) {
        if (wordMode) {
            string += "V";
            this.input.setText(string);
        }else {
            string = "V";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presB(ActionEvent q) {
        if (wordMode) {
            string += "B";
            this.input.setText(string);
        }else {
            string = "B";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);}
    }
    public void presN(ActionEvent q) {
        if (wordMode) {
            string += "N";
            this.input.setText(string);
        } else {
            string = "N";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);
        }
    }
    public void presM(ActionEvent q) {
        if (wordMode) {
            string += "M";
            this.input.setText(string);
        }else {
            string = "M";
            string2 = "";
            main();
            RotPos1.getValueFactory().setValue(rotorsPositions[0] + 1);
            RotPos2.getValueFactory().setValue(rotorsPositions[1] + 1);
            RotPos3.getValueFactory().setValue(rotorsPositions[2] + 1);
            this.input.setText(string);
        }
    }
    public void toggle(ActionEvent q) {
        wordMode = !wordMode;
        RSP[0] = rotorsPositions[0];
        RSP[1] = rotorsPositions[1];
        RSP[2] = rotorsPositions[2];
        fileMode = false;
        clearall(q);
    }
    public void clearall(ActionEvent q) {
        rotorsPositions[0] = RSP[0];
        rotorsPositions[1] = RSP[1];
        rotorsPositions[2] = RSP[2];
        RotPos1.getValueFactory().setValue(rotorsPositions[0]+1);
        RotPos2.getValueFactory().setValue(rotorsPositions[1]+1);
        RotPos3.getValueFactory().setValue(rotorsPositions[2]+1);
        string = "";
        string2 = "";
        this.input.setText(string);
        this.output.setText(string2);
    }
    public void initialize(){
        this.RotPos3.getStyleClass().add(STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
        this.RotPos2.getStyleClass().add(STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
        this.RotPos1.getStyleClass().add(STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
        this.rotorType1.getStyleClass().add(STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
        this.rotorType2.getStyleClass().add(STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
        this.rotorType3.getStyleClass().add(STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
    }
    public char com[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public void comA(char ch){
        com[0] = ch;
    }
    public void comE(char ch){
        com[4] = ch;
    }
    public void comF(char ch){
        com[5] = ch;
    }




    public void ApplyCommutator(char a, char b, char c, char d, char e, char f, char g, char h, char i, char j, char k, char l, char m,
                                char n, char o, char p, char q, char r, char s, char t, char u, char v, char w, char x, char y, char z) {
        /*System.out.printf("Before sum: %s", commSet);
        commSet += cA.getText().charAt(0);
        commSet += cB.getText().charAt(0);
        commSet += cC.getText().charAt(0);
        commSet += cD.getText().charAt(0);
        commSet += cE.getText().charAt(0);
        commSet += cF.getText().charAt(0);
        commSet += cG.getText().charAt(0);
        commSet += cH.getText().charAt(0);
        commSet += cI.getText().charAt(0);
        commSet += cJ.getText().charAt(0);
        commSet += cK.getText().charAt(0);
        commSet += cL.getText().charAt(0);
        commSet += cM.getText().charAt(0);
        commSet += cN.getText().charAt(0);
        commSet += cO.getText().charAt(0);
        commSet += cP.getText().charAt(0);
        commSet += cQ.getText().charAt(0);
        commSet += cR.getText().charAt(0);
        commSet += cS.getText().charAt(0);
        commSet += cT.getText().charAt(0);
        commSet += cU.getText().charAt(0);
        commSet += cV.getText().charAt(0);
        commSet += cW.getText().charAt(0);
        commSet += cX.getText().charAt(0);
        commSet += cY.getText().charAt(0);
        commSet += cZ.getText().charAt(0);
        System.out.printf("After sum: %s", commSet);*/
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        com[0] = a;
        com[1] = b;
        com[2] = c;
        com[3] = d;
        com[4] = e;
        com[5] = f;
        com[6] = g;
        com[7] = h;
        com[8] = i;
        com[9] = j;
        com[10] = k;
        com[11] = l;
        com[12] = m;
        com[13] = n;
        com[14] = o;
        com[15] = p;
        com[16] = q;
        com[17] = r;
        com[18] = s;
        com[19] = t;
        com[20] = u;
        com[21] = v;
        com[22] = w;
        com[23] = x;
        com[24] = y;
        com[25] = z;
        for (int count = 0; count<26; count++){
            setCommutator(abc.charAt(count), com[count]);
        }
    }
    public void comDefault(){
        clearCommutator();
        for (int i = 0; i<26; i++){
            com[i] = (char)('A'+i);
        }
    }
    public void newWindow() throws Exception {
        Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        AnchorPane oblast = new AnchorPane();
        Button closeCom = new Button("Закрыть");
        closeCom.setLayoutX(330);
        closeCom.setLayoutY(10);
        closeCom.setOnAction(event->stage.close());
        Button defaultCom = new Button("Сброс");
        defaultCom.setLayoutX(175);
        defaultCom.setLayoutY(10);
        defaultCom.setOnAction(event->comDefault());

        TextField cA = new TextField();
        TextField cB = new TextField();
        TextField cC = new TextField();
        TextField cD = new TextField();
        TextField cE = new TextField();
        TextField cF = new TextField();
        TextField cG = new TextField();
        TextField cH = new TextField();
        TextField cI = new TextField();
        TextField cJ = new TextField();
        TextField cK = new TextField();
        TextField cL = new TextField();
        TextField cM = new TextField();
        TextField cN = new TextField();
        TextField cO = new TextField();
        TextField cP = new TextField();
        TextField cQ = new TextField();
        TextField cR = new TextField();
        TextField cS = new TextField();
        TextField cT = new TextField();
        TextField cU = new TextField();
        TextField cV = new TextField();
        TextField cW = new TextField();
        TextField cX = new TextField();
        TextField cY = new TextField();
        TextField cZ = new TextField();

        cA.setText((String.valueOf(com[0])));
        cB.setText((String.valueOf(com[1])));
        cC.setText((String.valueOf(com[2])));
        cD.setText((String.valueOf(com[3])));
        cE.setText((String.valueOf(com[4])));
        cF.setText((String.valueOf(com[5])));
        cG.setText((String.valueOf(com[6])));
        cH.setText((String.valueOf(com[7])));
        cI.setText((String.valueOf(com[8])));
        cJ.setText((String.valueOf(com[9])));
        cK.setText((String.valueOf(com[10])));
        cL.setText((String.valueOf(com[11])));
        cM.setText((String.valueOf(com[12])));
        cN.setText((String.valueOf(com[13])));
        cO.setText((String.valueOf(com[14])));
        cP.setText((String.valueOf(com[15])));
        cQ.setText((String.valueOf(com[16])));
        cR.setText((String.valueOf(com[17])));
        cS.setText((String.valueOf(com[18])));
        cT.setText((String.valueOf(com[19])));
        cU.setText((String.valueOf(com[20])));
        cV.setText((String.valueOf(com[21])));
        cW.setText((String.valueOf(com[22])));
        cX.setText((String.valueOf(com[23])));
        cY.setText((String.valueOf(com[24])));
        cZ.setText((String.valueOf(com[25])));
        /*cA.setText("A");
        cB.setText("B");
        cC.setText("C");
        cD.setText("D");
        cE.setText("E");
        cF.setText("F");
        cG.setText("G");
        cH.setText("H");
        cI.setText("I");
        cJ.setText("J");
        cK.setText("K");
        cL.setText("L");
        cM.setText("M");
        cN.setText("N");
        cO.setText("O");
        cP.setText("P");
        cQ.setText("Q");
        cR.setText("R");
        cS.setText("S");
        cT.setText("T");
        cU.setText("U");
        cV.setText("V");
        cW.setText("W");
        cX.setText("X");
        cY.setText("Y");
        cZ.setText("Z");*/
        Label Q = new Label("Q");
        Label W = new Label("W");
        Label E = new Label("E");
        Label R = new Label("R");
        Label T = new Label("T");
        Label Y = new Label("Y");
        Label U = new Label("U");
        Label I = new Label("I");
        Label O = new Label("O");
        Label P = new Label("P");
        Label A = new Label("A");
        Label S = new Label("S");
        Label D = new Label("D");
        Label F = new Label("F");
        Label G = new Label("G");
        Label H = new Label("H");
        Label J = new Label("J");
        Label K = new Label("K");
        Label L = new Label("L");
        Label Z = new Label("Z");
        Label X = new Label("X");
        Label C = new Label("C");
        Label V = new Label("V");
        Label B = new Label("B");
        Label N = new Label("N");
        Label M = new Label("M");

        Button applyCom = new Button("Применить");
        applyCom.setLayoutX(240);
        applyCom.setLayoutY(10);
        applyCom.setOnAction(event->ApplyCommutator(cA.getText().charAt(0),
                cB.getText().charAt(0),
                cC.getText().charAt(0),
                cD.getText().charAt(0),
                cE.getText().charAt(0),
                cF.getText().charAt(0),
                cG.getText().charAt(0),
                cH.getText().charAt(0),
                cI.getText().charAt(0),
                cJ.getText().charAt(0),
                cK.getText().charAt(0),
                cL.getText().charAt(0),
                cM.getText().charAt(0),
                cN.getText().charAt(0),
                cO.getText().charAt(0),
                cP.getText().charAt(0),
                cQ.getText().charAt(0),
                cR.getText().charAt(0),
                cS.getText().charAt(0),
                cT.getText().charAt(0),
                cU.getText().charAt(0),
                cV.getText().charAt(0),
                cW.getText().charAt(0),
                cX.getText().charAt(0),
                cY.getText().charAt(0),
                cZ.getText().charAt(0)));

        A.setLayoutX(14);
        A.setLayoutY(14);

        B.setLayoutX(14);
        B.setLayoutY(40);

        C.setLayoutX(14);
        C.setLayoutY(66);

        D.setLayoutX(14);
        D.setLayoutY(92);

        E.setLayoutX(14);
        E.setLayoutY(118);

        F.setLayoutX(14);
        F.setLayoutY(144);

        G.setLayoutX(85);
        G.setLayoutY(40);

        H.setLayoutX(85);
        H.setLayoutY(66);

        J.setLayoutX(85);
        J.setLayoutY(92);

        I.setLayoutX(85);
        I.setLayoutY(118);

        K.setLayoutX(85);
        K.setLayoutY(144);

        L.setLayoutX(162);
        L.setLayoutY(40);

        M.setLayoutX(162);
        M.setLayoutY(66);

        N.setLayoutX(162);
        N.setLayoutY(92);

        O.setLayoutX(162);
        O.setLayoutY(118);

        P.setLayoutX(162);
        P.setLayoutY(144);

        Q.setLayoutX(232);
        Q.setLayoutY(40);

        R.setLayoutX(232);
        R.setLayoutY(66);

        S.setLayoutX(232);
        S.setLayoutY(92);

        T.setLayoutX(232);
        T.setLayoutY(118);

        U.setLayoutX(232);
        U.setLayoutY(144);

        V.setLayoutX(309);
        V.setLayoutY(40);

        W.setLayoutX(309);
        W.setLayoutY(66);

        X.setLayoutX(309);
        X.setLayoutY(92);

        Y.setLayoutX(309);
        Y.setLayoutY(118);

        Z.setLayoutX(309);
        Z.setLayoutY(144);

        cA.setMaxWidth(25);
        cA.setLayoutX(43);
        cA.setLayoutY(14);

        cB.setMaxWidth(25);
        cB.setLayoutX(43);
        cB.setLayoutY(40);

        cC.setMaxWidth(25);
        cC.setLayoutX(43);
        cC.setLayoutY(66);

        cD.setMaxWidth(25);
        cD.setLayoutX(43);
        cD.setLayoutY(92);

        cE.setMaxWidth(25);
        cE.setLayoutX(43);
        cE.setLayoutY(118);

        cF.setMaxWidth(25);
        cF.setLayoutX(43);
        cF.setLayoutY(144);

        cG.setMaxWidth(25);
        cG.setLayoutX(115);
        cG.setLayoutY(40);

        cH.setMaxWidth(25);
        cH.setLayoutX(115);
        cH.setLayoutY(66);

        cI.setMaxWidth(25);
        cI.setLayoutX(115);
        cI.setLayoutY(92);

        cJ.setMaxWidth(25);
        cJ.setLayoutX(115);
        cJ.setLayoutY(118);

        cK.setMaxWidth(25);
        cK.setLayoutX(115);
        cK.setLayoutY(144);

        cL.setMaxWidth(25);
        cL.setLayoutX(185);
        cL.setLayoutY(40);

        cM.setMaxWidth(25);
        cM.setLayoutX(185);
        cM.setLayoutY(66);

        cN.setMaxWidth(25);
        cN.setLayoutX(185);
        cN.setLayoutY(92);

        cO.setMaxWidth(25);
        cO.setLayoutX(185);
        cO.setLayoutY(118);

        cP.setMaxWidth(25);
        cP.setLayoutX(185);
        cP.setLayoutY(144);

        cQ.setMaxWidth(25);
        cQ.setLayoutX(260);
        cQ.setLayoutY(40);

        cR.setMaxWidth(25);
        cR.setLayoutX(260);
        cR.setLayoutY(66);

        cS.setMaxWidth(25);
        cS.setLayoutX(260);
        cS.setLayoutY(92);

        cT.setMaxWidth(25);
        cT.setLayoutX(260);
        cT.setLayoutY(118);

        cU.setMaxWidth(25);
        cU.setLayoutX(260);
        cU.setLayoutY(144);

        cV.setMaxWidth(25);
        cV.setLayoutX(335);
        cV.setLayoutY(40);

        cW.setMaxWidth(25);
        cW.setLayoutX(335);
        cW.setLayoutY(66);

        cX.setMaxWidth(25);
        cX.setLayoutX(335);
        cX.setLayoutY(92);

        cY.setMaxWidth(25);
        cY.setLayoutX(335);
        cY.setLayoutY(118);

        cZ.setMaxWidth(25);
        cZ.setLayoutX(335);
        cZ.setLayoutY(144);
        oblast.getChildren().addAll(defaultCom, applyCom, closeCom,cA,cB,cC,cD,cE,cF,cG,cH,cJ,cI,cK,cL,cM,cN,cO,cP,cQ,cR,cS,cT,cU,cV,cW,cX,cY,cZ,A,B,C,D,E,F,G,H,J,I,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z);

        stage.setScene(new Scene(oblast, 400, 180));
        stage.setTitle("title");
        stage.showAndWait();
    }

    public void cWindow(ActionEvent q) throws Exception {
        newWindow();
    }

}