/*
 *  - 4 cavaleiros caminham por um corredor, simultaneamente, de 2 a 4m a cada 50ms.
 *  - O corredor é escuro, tem 2km e em 500m, há uma única tocha. 
 *  - O cavaleiro que pegar a tocha, aumenta sua velocidade, somando mais 2m por 50ms ao valor que já fazia. 
 *  - Em 1,5km, existe uma pedra brilhante. O cavaleiro que pegar a pedra, aumenta sua velocidade, 
 *  	somando mais 2m por 50ms  ao  valor  que  já  fazia. 
 *  - Ao  final  dos  2 km,  os  cavaleiros  se  separam  em 4 corredores cada um com uma porta e,
 *      um  por vez  pega  uma  porta aleatória  (que  não  pode repetir)  e  entra  nela.  
 *  - Apenas  uma  porta  leva  à  saída.  As  outras  3  tem  monstros  que  os devoram. 
 */

package view;

import java.util.concurrent.Semaphore;

import controller.ThreadCavaleiro;

public class Principal {

	public static void main(String[] args) {
		int limitacao = 1;
		Semaphore semaforo = new Semaphore(limitacao);
		for(int codCavaleiro = 0; codCavaleiro < 4; codCavaleiro ++)/*inicia os 4 cavaleiros*/ {
			Thread tc = new ThreadCavaleiro(semaforo, codCavaleiro );
			tc.start();
		}
	}
}
