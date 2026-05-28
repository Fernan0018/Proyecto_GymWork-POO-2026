package com.mycompany.proyecto_final_poo;
import javax.swing.*;
import java.awt.*;



public class Marca_de_agua extends javax.swing.JPanel {
    private Image logo;
    private float alpha = 0.15f;
    
    
    public Marca_de_agua(){
        setOpaque(false);
       logo = new ImageIcon("datos/Images/marcadeagua-Photoroom.png").getImage();
    }
    
    public void setAlpha(float alpha) {
        this.alpha = alpha;
        repaint();
    }
     
    
    @Override
    protected void paintComponent(Graphics g){
     super.paintComponent(g);
     
     Graphics2D g2d = (Graphics2D)g;
    
     g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
     
     int x = getWidth() - logo.getWidth(this)  ;
     int y = (getHeight() -logo.getHeight(this))/2;
    
     
     g2d.drawImage(logo,x,y,this);
    } 
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\marcadeagua-Photoroom.png")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
