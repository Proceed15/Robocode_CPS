package wrecker;
import robocode.*;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import java.awt.*;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

// Wrecker - A robot made by - José Roberto Lisboa da Silva Filho/ João Vitor Aparecido Carpim de Souza/ Luiz Gabriel Rodrigues Frei.

/**
 * Wrecker - a robot by (your name here)
 */
public class Wrecker extends AdvancedRobot {
    int moveDirection=1;//Qual sentido se mexe.
    /**
     * Roda o Wrecker.
     */
    public void run() {
        setColors( Color.white, Color.red, Color.white );
        setScanColor(Color.white);
        setBulletColor(Color.blue);
        setAdjustGunForRobotTurn(true); // O canhão gira na posição oposta do radar.
        turnRadarRightRadians(Double.POSITIVE_INFINITY);//Mantém o radar girando.
         double v = 5;
          double c = Math.PI*2;
         double a = .1;
         double b = .0053468;
    
          setMaxVelocity(v);
          setAhead(100*999);
          setTurnRight(360*999);
          while(true)//Para se mover em curvas.
          {
              double t = getTime();
              double f = a*Math.pow(Math.E,b*t);
              double w = v/(c*f);       
    
              setMaxTurnRate(w);
              execute();
              System.out.println(t+"\t"+w);
          }
    }
    
    /**
     * Ve o robo e vai atrás.
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        double absBearing=e.getBearingRadians()+getHeadingRadians();//Comportamento/trajetória do inimigo.
        double latVel=e.getVelocity() * Math.sin(e.getHeadingRadians() -absBearing);//Velocidade do inimigo.
        double gunTurnAmt;//Quantidade de giro do canhão.
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());//Leitura do radar focada no inimigo.
        if(Math.random()>.9){
            setMaxVelocity((12*Math.random())+12);//Muda a velocidade aleatoriamente durante a batalha.
        }
        if (e.getDistance() > 200) {//Se estiver há mais de 200.
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/22);//Quantidade de giro do canhão em relação ao inimigo.
            setTurnGunRightRadians(gunTurnAmt); //Gira o canhão.
            setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing-getHeadingRadians()+latVel/getVelocity()));//Vai em direção a futura posição (prevista) do inimigo.
            setAhead((e.getDistance() - 140)*moveDirection);//Anda em frente.
            setFire(2);//Atira.
        }
        else if (e.getDistance() > 150) {//Se estiver há mais de 150.
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/22);//Quantidade de giro do canhão em relação ao inimigo.
            setTurnGunRightRadians(gunTurnAmt); //Gira o canhão.
            setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing-getHeadingRadians()+latVel/getVelocity()));//Vai em direção a futura posição (prevista) do inimigo.
            setAhead((e.getDistance() - 140)*moveDirection);//Anda em frente.
            setFire(3);//Atira.
        }
        else{//Se estiver no alcance de menos de 150.
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/15);//Quantidade de giro do canhão em relação ao inimigo.
            setTurnGunRightRadians(gunTurnAmt);//Gira o canhão.
            setTurnLeft(-90-e.getBearing()); //Gira em volta do inimigo.
            setAhead((e.getDistance() - 140)*moveDirection);//Anda em frente.
            setFire(3);//fire
        }
    }
    public void onHitWall(HitWallEvent e){
        moveDirection=-moveDirection;//Reverte a direção se acertar uma parede.
    }
    /**
     * Se vencer faz a dança da vitória.
     */
    public void onWin(WinEvent e) {
        for (int i = 0; i < 50; i++) {
            turnRight(30);
            turnLeft(30);
        }
    }
	}