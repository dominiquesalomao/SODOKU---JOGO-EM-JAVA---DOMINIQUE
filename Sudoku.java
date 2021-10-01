package javaapplication1;

import java.lang.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Sudoku
{
    int mat[][];
    int N; // numero de colunas e linhas
    int QN; // raiz quadrada de n 
    int K; // numero de digitos ausentes
 
    // construtor
    Sudoku(int N, int K)
    {
        this.N = N;
        this.K = K;
 
        // Calcula a raiz quadrada de n 
        Double SRNd = Math.sqrt(N);
        QN = SRNd.intValue();
 
        mat = new int[N][N];
    }
 
    // gerador de sudoku
    public void preencheValores()
    {                                                    
        // Preenche a diagonal das matrizes QN x QN 
        preencheDiagonais();
 
        // Preenche os blocos restantes
        preencheRestante(0, QN);
 
        // remove aleatoriamente k digitos 
        removeKDigitos();
    }
 
    // Preenche a diagonal das matrizes QN x QN 
    void preencheDiagonais(){
        for (int i = 0; i<N; i=i+QN)
            // para as caixas diagonais , iniciar as cordenadas i == j
            preencherCaixa(i, i);
    }
 
    // Verifica bloco
    boolean verificarBloco(int linhaInicial, int colunaInicial, int num){
        for (int i = 0; i<QN; i++)
            for (int j = 0; j<QN; j++)
                if (mat[linhaInicial+i][colunaInicial+j]==num)
                    return false;
        return true;
    }
 
    // Preenche matriz 3x3
    void preencherCaixa(int linha,int coluna){
        int num;
        for (int i=0; i<QN; i++){
            for (int j=0; j<QN; j++){
                do{
                    num = gerarNAleatorio(N);
                }while (!verificarBloco(linha, coluna, num));
                mat[linha+i][coluna+j] = num;
            }
        }
    }
 
    // Gera número aleatório
    int gerarNAleatorio(int num){
        return (int) Math.floor((Math.random()*num+1));
    }
 
    // Verifica se é seguro colocar uma célula
    boolean verificarSeguranca(int i,int j,int num){
        return (verificaLinha(i, num) &&
                verificaColuna(j, num) &&
                verificarBloco(i-i%QN, j-j%QN, num));
    }
 
    // // Verifica a existência na linha
    boolean verificaLinha(int i,int num){
        for (int j = 0; j<N; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }
 
    // // Verifica a existência na coluna
    boolean verificaColuna(int j,int num){
        for (int i = 0; i<N; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }
 
    // Preenche o restante da matriz
    boolean preencheRestante(int i, int j){
        if (j>=N && i<N-1){
            i = i + 1;
            j = 0;
        }
        if (i>=N && j>=N)
            return true;
        if (i < QN){
            if (j < QN)
                j = QN;
        }else if (i < N-QN){
            if (j==(int)(i/QN)*QN)
                j =  j + QN;
        }else{
            if (j == N-QN){
                i = i + 1;
                j = 0;
                if (i>=N)
                    return true;
            }
        }
        for (int num = 1; num<=N; num++){
            if (verificarSeguranca(i, j, num)){
                mat[i][j] = num;
                if (preencheRestante(i, j+1))
                    return true;
                mat[i][j] = 0;
            }
        }
        return false;
    }
 
    //Remove k numeros do tabuleiro
    public void removeKDigitos(){
        int count = K;
        while (count != 0){
            int celulaId = gerarNAleatorio(N*N)-1;
            int i = (celulaId/N);
            int j = celulaId%9;
            if (j != 0)
                j = j - 1;
            if (mat[i][j] != 0){
                count--;
                mat[i][j] = 0;
            }
        }
    }
 
    // Exbie o jogo
    public void mostrarSudoku(){
        for (int i = 0; i<N; i++){
            for (int j = 0; j<N; j++){
                if(mat[i][j]!= 0 ){
                    System.out.print(mat[i][j]);
                }else{
                    System.out.print(".");
                }
                if(j == 2 || j == 5 || j==8)
                    System.out.print("|");
            }
            System.out.println();
            if(i == 2 || i == 5 || i == 8)
                System.out.println("--- --- ---");
        }
        System.out.println();
    }
    
    //Verifica o sudoku quando completo
    public boolean verificarSudokuCompleto(int n){
        //verificar linha 
        for(int i = 0 ; i < n ; i ++){
            for(int j=0;j<n;j++){
                for(int k=0;k<n;k++){
                    if(j!=k  && this.mat[i][j] == this.mat[i][k]){
                        return false;
                    }
                }
            }
        }        
        //verinha coluna(soma todos os numeros da coluna, e verifica se é igual ao total, se não for, há número repetido)
         for(int i = 0 ; i < n ; i ++){
            for(int j=0;j<n;j++){
                for(int k=0;k<n;k++){
                    if(j!=k  && this.mat[j][i] == this.mat[k][i]){
                        return false;
                    }
                }
            }
        }            
        //verificar quadrante
        Double SRNd = Math.sqrt(N);
        int q = SRNd.intValue(), aux = q;
        ArrayList<Integer> linha = new ArrayList<Integer>();
        for(int j=0; j < n ; j ++){
            for ( int i = q-3 ; i < q ; i = i+2){
                for (int k= aux-3; i < aux ; k++){
                   linha.add(this.mat[i][k]); 
                }
            }
            
            //verifica linha
            int contador;
                for (int i=1; i<10; i ++) {
                    contador = 0;
                    for(Integer l : linha){
                        if(l == i){
                            contador++;
                        }
                    }
                    if(contador > 1 || contador == 0){
                        return false;
                    }
                }   
            
            //
            
            if(j<q){
                q = 3;
            }else{
                int r = j/3;
                q= (3*r) + 3;
            }
            aux +=3;
            if(aux > 9){
                aux = 0;
            }
        }
        
         
        return true;
    }
    
    // Driver code
    public static void main(String[] args)
    {
        Scanner ler = new Scanner(System.in);
        int dificuldade, y, x, n = 9, k = 65, valor;
        boolean fim = false;
        System.out.println("Bem vindo ao sudoku!");
        System.out.println("Qual grau de dificuldade voce deseja jogar?");
        System.out.println("1 - Facil | 2 - Intermediario | 3 - Dificil");
        dificuldade = ler.nextInt();
        switch(dificuldade) {
            case 1:
                k = 35;
                System.out.println("\n\n\n\n\n\n");
            break;
            case 2:
                 k = 50;
                System.out.println("\n\n\n\n\n\n");
            break;
            case 3:
                k = 65;
                System.out.println("\n\n\n\n\n\n");
            break;
            default:
                System.out.println("\n\n\n\n\n\n");
                System.out.println("Voce não digitou uma opcao e jogara o modo dificil!\n");
            break;
        }
        Sudoku sudoku = new Sudoku(n, k);
        sudoku.preencheValores();
        sudoku.mostrarSudoku();
        int mat_fixo[][] = new int [n][n] ;
        for(int i = 0 ; i < n ; i ++){
            for(int j = 0 ; j < n ; j ++){
                mat_fixo[i][j] =  sudoku.mat[i][j];
            }
        }
        do{
            System.out.println("Qual linha voce deseja alterar? (De 1 a 9 )");
            x = ler.nextInt();
            System.out.println("Qual coluna voce deseja alterar? (De 1 a 9 )");
            y = ler.nextInt();
            if(y > 9 || y <= 0 || x > 9 || x <= 0 ){
                System.out.println("A linha e a coluna devem ser maiores que zero e menores que 10");
            }else{
                if(mat_fixo[x-1][y-1] != 0){
                    System.out.println("Essa coluna e linha não podem ser alteradas!");
                }else{
                    System.out.println("Qual sera o novo valor?");
                    boolean valor_correto = false;
                    do{
                        valor = ler.nextInt();
                        if(valor < 0 || valor >9){
                            System.out.println("O valor deve ser maior que zero e menor que 10");
                        }else{
                            valor_correto = true;
                            sudoku.mat[x-1][y-1] = valor;
                            System.out.println("");
                            sudoku.mostrarSudoku();
                        }
                    }while(valor_correto == false);
                }
            }
            fim = true;
            for(int i = 0 ; i < n ; i ++){
                for(int j = 0 ; j < n ; j ++){
                    if(sudoku.mat[i][j] == 0){
                        fim = false;
                    }
                }
            }
            if(fim == true){
                fim = sudoku.verificarSudokuCompleto(n);
                if(fim == false){
                     System.out.println("O tabuleiro esta completo, mas esta incorreto, altere os campos ate finalizar corretamente!");
                }
            }     
        }while(fim == false);
        System.out.println("Voce concluiu o tabuleiro! Parabens!");
    }
}