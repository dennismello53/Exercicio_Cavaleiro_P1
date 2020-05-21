/*
 *  - 4 cavaleiros caminham por um corredor, simultaneamente, de 2 a 4m por 50ms.
 *  - O corredor é escuro, tem 2km e em 500m, há uma única tocha. 
 *  - O cavaleiro que pegar a tocha, aumenta sua velocidade, somando mais 2m por 50ms ao valor que já fazia. 
 *  - Em 1,5km, existe uma pedra brilhante. O cavaleiro que pegar a pedra, aumenta sua velocidade, 
 *  	somando mais 2m por 50ms  ao  valor  que  já  fazia. 
 *  - Ao  final  dos  2 km,  os  cavaleiros  se  separam  em 4 corredores cada um com uma porta e,
 *      um  por vez  pega  uma  porta aleatória  (que  não  pode repetir)  e  entra  nela.  
 *  - Apenas  uma  porta  leva  à  saída.  As  outras  3  tem  monstros  que  os devoram. 
 */

package controller;

import java.util.concurrent.Semaphore;

public class ThreadCavaleiro extends Thread {
	
	private Semaphore semaforo;
	private int codCavaleiro;
	//Inventario do corredor
	private static boolean tocha = true; 
	private static boolean pedra = true;
	//-----------------------------------
	private static boolean [] portas = {true, true, true, true};
	private static int viver = (int)(Math.random()*4)+0;
	
	public ThreadCavaleiro(Semaphore semaforo, int codCavaleiro) {
		this.semaforo = semaforo;
		this.codCavaleiro = codCavaleiro;
	}
	
	private void caminhada() {
		int corredor = 2000; //Distancia total do corredor
		int distancia = 0; //Distancia percorrida pelos cavaleiros
		//Inventario do cavaleiro
		boolean tocha = false; 
		boolean pedra = false;
		//----------------------
		while(distancia < corredor) { //Rotina da caminhada
			distancia+= (int)(Math.random()*3)+2;
			if(distancia >= 500 && this.tocha){//Verifica se ele chegou a distancia da tocha, e se ela ainda se encontra no corredor
				tocha = true;//Adiciona a tocha ao inventario do cavaleiro
				this.tocha = false;//Remove a tocha ao inventario do corredor
				System.out.println("O Cavaleiro#" + codCavaleiro + " achou uma tocha no escuro e se sentiu motivado ganhando - +2 SPD");
			}
			if(distancia >= 1500 && this.pedra) {//Verifica se ele chegou a distancia da pedra, e se ela ainda se encontra no corredor
				pedra = true;//Adiciona a pedra ao inventario do cavaleiro
				this.pedra = false;//Remove a pedra do inventario do corredor
				System.out.println("O Cavaleiro#" + codCavaleiro + " achou uma pedra brilhante o enchendo de determinação - +2 SPD");
			}
			//Adiciona 2 de velocidade se houver algum dos itens no inventario
			if(tocha) distancia+=2;
			if(pedra) distancia+=2;
			//----------------------
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//------------- Fim da caminhada
		System.out.println("O Cavaleiro#" + codCavaleiro + " bate na porta... TOC TOC, tem um monstro ai dentro?");
	}//Final da caminhada
	
	private void entrarPorta() {
		boolean entrou = false;
		while(!entrou) {//Tenta entrar na portas até achar uma vazia
			int porta = (int)(Math.random()*4)+0;
			if(portas[porta]) {//Verifica se a porta está vazia
				entrou = true;//Cavaleiro entrou na porta
				portas[porta] = false;//Fecha a porta para os outros cavaleiros
				//Verifica se há um monstro na porta
				if (porta == viver) System.out.println("O Cavaleiro#" + codCavaleiro + " tirou 20 no dado e sobreviveu ao desafio");
				else System.out.println("O Cavaleiro#" + codCavaleiro + " tirou 1 no dado e pereceu nas mãos do Beholder");
				//------------------------------------------------------------------------------------------------------------------
			}
		}
	}

	@Override//----------------------------------------------------------------------------------------------------------------------
	public void run() {
		caminhada();//Começa a caminhada pelo corredor
		try {
			semaforo.acquire();//Limita as Threads
			entrarPorta();//Cavaleiro entra na porta
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaforo.release();//Libera as Threads
		}
	}
}
