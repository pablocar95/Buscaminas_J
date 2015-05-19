
package Principal;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Pablo
 */
public class VentanaBuscaminas2 extends javax.swing.JFrame {

    //primero resuelvo la vista para que me pinte
    //una pantalla con botones
    int filas = 20;
    int columnas = 30;
    int numMinas = 59;
    Icon iconFlag = new ImageIcon(getClass().getResource("flag.png"));
    Icon iconMina = new ImageIcon(getClass().getResource("mina.png"));
    Boton[][] arrayBotones = new Boton[filas][columnas];
    boolean gameOver = false;

    private void ponUnaBomba() {
        Random r = new Random();
        int f = r.nextInt(filas);
        int c = r.nextInt(columnas);
        arrayBotones[f][c].bomba = 1;
    }

    //cuentaminas realiza un paso previo que consiste en contar para cada celda
    //el numero de minas que hay alrededor
    private void cuentaMinas() {
        int minas = 0;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                //uso un bucle anidado para recorrer
                //las 8 casillas que hay alrededor
                for (int k = -1; k < 2; k++) {
                    for (int m = -1; m < 2; m++) {
                        if ((i + k >= 0) && (j + m >= 0) && (i + k < filas) && (j + m < columnas)) {
                            minas = minas + arrayBotones[i + k][j + m].bomba;
                        }
                    }
                }
                arrayBotones[i][j].numeroMinasAlrededor = minas;
                minas = 0;
                if ((arrayBotones[i][j].numeroMinasAlrededor > 0)
                        && (arrayBotones[i][j].bomba == 0)) {
                    arrayBotones[i][j].setText("");
                }
            }
        }

    }

    /**
     * Creates new form VentanaBuscaminas
     */
    public VentanaBuscaminas2() {
        initComponents();
        setSize(1280, 1024);
        //le digo al jFrame que va a usar un layout de rejilla
        getContentPane().setLayout(new GridLayout(filas, columnas));
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Boton boton = new Boton(i, j);
                boton.setBorder(null);
                //añado el evento del clic del ratón
                boton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent evt) {
                        //metodo a llamar cuando se pulse el botón

                        botonPulsado(evt);

                    }
                });
                //añado el botón a mi array de botones
                arrayBotones[i][j] = boton;
                //añado el botón a la pantalla
                getContentPane().add(boton);
            }
        }
        for (int i = 0; i < numMinas; i++) {
            ponUnaBomba();
        }
        cuentaMinas();
    }

    private void finDePartida() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Boton boton = arrayBotones[i] [j];
                if (boton.bomba == 1) {
                    boton.setIcon(iconMina);
                    boton.setEnabled(false);
                } else {
                    if(boton.numeroMinasAlrededor>0)
                    boton.setText(boton.numeroMinasAlrededor + "");
                    boton.setEnabled(false);
                }
            }
        }

    }

    //este método es llamado cada vez que hacemos clic en un botón
    private void botonPulsado(MouseEvent e) {
        if (!gameOver) {
            Boton miBoton = (Boton) e.getComponent();
            if (e.getButton() == MouseEvent.BUTTON3) {
                //miBoton.setText("?");
                miBoton.setIcon(iconFlag);
            } else {
            //si es una bomba --> explota y se acaba la partid
                //declaro un arraylist para ir guardando la lista de botones
                //que tengo que verificai
                if (miBoton.bomba == 1) {
                    System.out.println("SE ACABO");
                    miBoton.setFocusPainted(false);
                    finDePartida();
                    gameOver=true;
                } else {
                    System.out.println("TE SALVASTE");
                    if (miBoton.numeroMinasAlrededor>0)
                    miBoton.setText(miBoton.numeroMinasAlrededor+"");
                    ArrayList<Boton> listaDeCasillasAMirar = new ArrayList();
                    //añado el botón que ha sido pulsado
                    listaDeCasillasAMirar.add(miBoton);

                    while (listaDeCasillasAMirar.size() > 0) {
                        Boton b = listaDeCasillasAMirar.get(0);
                        for (int k = -1; k < 2; k++) {
                            for (int m = -1; m < 2; m++) {
                                if ((b.x + k >= 0)
                                        && (b.y + m >= 0)
                                        && (b.x + k < filas)
                                        && (b.y + m < columnas)) {
                                //si el boton de esa posición está habilitado 
                                    //es que no lo he chequeado todavia
                                    if (arrayBotones[b.x + k][b.y + m].isEnabled()) {
                                        if (arrayBotones[b.x + k][b.y + m].numeroMinasAlrededor == 0) {
                                            arrayBotones[b.x + k][b.y + m].setEnabled(false);
                                            listaDeCasillasAMirar.add(arrayBotones[b.x + k][b.y + m]);
                                        }
                                    }
                                }
                            }
                        }
                        listaDeCasillasAMirar.remove(b);
                    }

                }
                //si no, verificamos la casilla 
                miBoton.setFocusPainted(false);

            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaBuscaminas2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
} 


