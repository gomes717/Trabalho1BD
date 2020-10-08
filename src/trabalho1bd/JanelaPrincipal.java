/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho1bd;

import com.mysql.cj.jdbc.DatabaseMetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author gomes
 */
public class JanelaPrincipal extends javax.swing.JFrame {

    protected String login;
    protected String senha;
    protected String url;
    protected Connection con;
    protected Statement stmt;
    private javax.swing.JTree jTree;
    
    public JanelaPrincipal(String login, String senha, String url) {
        initComponents();
        this.setTitle("Janela Principal");
        //passagem de parametros da janela login
        this.login = login;
        this.senha = senha;
        this.url = url;

        try{
        con = DriverManager.getConnection("jdbc:"+url, login, senha);
        stmt = con.createStatement();
        updateTree();
        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
    
    
    private void updateTree()
    {
        ResultSet rs;
        //criar root da arvore
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(url);
        try{
            java.sql.DatabaseMetaData metadata = con.getMetaData();
            rs =  metadata.getCatalogs();
            while(rs.next())
            {   
                String auxName = rs.getString(1);
                DefaultMutableTreeNode auxNode = new DefaultMutableTreeNode(auxName);
                root.add(auxNode);
                DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode("Tables");
                DefaultMutableTreeNode viewNode = new DefaultMutableTreeNode("views");
                auxNode.add(tableNode);
                auxNode.add(viewNode);
                ResultSet rsAux = metadata.getTables(rs.getString(1), null, "%", new String[] {"TABLE"});
                
                
                while(rsAux.next())
                {
                    String tmpName = rsAux.getString(3);
                    DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode(tmpName);
                    tableNode.add(tmpNode);
                    DefaultMutableTreeNode colNode = new DefaultMutableTreeNode("Columns");
                    DefaultMutableTreeNode indNode = new DefaultMutableTreeNode("Indexes");
                    DefaultMutableTreeNode fkNode = new DefaultMutableTreeNode("Foreign Keys");
                    DefaultMutableTreeNode trigNode = new DefaultMutableTreeNode("Triggers");
                    
                    tmpNode.add(colNode);
                    tmpNode.add(indNode);
                    tmpNode.add(fkNode);
                    tmpNode.add(trigNode);
                    ResultSet rsTable = metadata.getColumns(null, null, rsAux.getString(3), null);
                    while(rsTable.next())
                    {
                        colNode.add(new DefaultMutableTreeNode(rsTable.getString(4)));
                    }
                    rsTable = metadata.getIndexInfo(null, null, rsAux.getString(3), true, false);
                    while(rsTable.next())
                    {
                        indNode.add(new DefaultMutableTreeNode(rsTable.getString(6)));
                    }
                    rsTable = metadata.getImportedKeys(null, null, rsAux.getString(3));
                    while(rsTable.next())
                    {
                        fkNode.add(new DefaultMutableTreeNode(rsTable.getString(12)));
                    }
                    rsTable = metadata.getTables(null, null, rsAux.getString(3), new String[] {"TRIGGER"});
                    while(rsTable.next())
                    {
                        fkNode.add(new DefaultMutableTreeNode(rsTable.getString(3)));
                    }
                }
                rsAux = metadata.getTables(rs.getString(1), null, "%", new String[] {"VIEW"});
                while(rsAux.next())
                {
                    String tmpName = rsAux.getString(3);
                    DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode(tmpName);
                    viewNode.add(tmpNode);
                    ResultSet rsView = metadata.getColumns(null, null, rsAux.getString(3), null);
                    while(rsView.next())
                    {
                        tmpNode.add(new DefaultMutableTreeNode(rsView.getString(4)));
                    }
                }
            }
            
        } catch(SQLException e)
        {
            System.out.println(e.getMessage() + "2");
        }
        jTree = new javax.swing.JTree(root);
        add(jTree);
        jTree.setRootVisible(false);
        jScrollPane2.setViewportView(jTree);
        this.pack();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Databases");

        jButton1.setText("R");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(867, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        updateTree();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
