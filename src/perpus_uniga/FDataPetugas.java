/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpus_uniga;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ASUS VIVOBOOK
 */
public class FDataPetugas extends javax.swing.JFrame {

    /**
     * Creates new form FDataPegawai
     */
    public FDataPetugas() {
        initComponents();
        load_data(); //Memanggil Menampilkan Data
        IDOtomatis();
    }
    
    //Load Data Dari Database tbl_anggota
    private void load_data()
    {
        Connection kon=koneksi.koneksiDb();
        Object header[]={"ID PETUGAS","NAMA PETUGAS","TEMPAT LAHIR","TANGGAL LAHIR","JENIS KELAMIN","ALAMAT","NO HP","EMAIL","JABATAN"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        TabelPetugas.setModel (data);
        String sql_data="SELECT * FROM t_petugas";
        try
        {
            Statement st=kon.createStatement();
            ResultSet rs=st.executeQuery(sql_data);
            while(rs.next())
            {
                String d1=rs.getString(1);
                String d2=rs.getString(2);
                String d3=rs.getString(3);
                String d4=rs.getString(4);
                String d5=rs.getString(5);
                String d6=rs.getString(6);
                String d7=rs.getString(7);
                String d8=rs.getString(8);
                String d9=rs.getString(9);
                
                
                String d[]={d1,d2,d3,d4,d5,d6,d7,d8,d9,};
                data.addRow(d);
            
            }
        }
        catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
    }
    
    //ID Anggota Otomatis
    private void IDOtomatis()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_id="SELECT * FROM t_petugas order by id_petugas desc";
            ResultSet rs=st.executeQuery(sql_id);
            if(rs.next())
            {
                String id_petugas=rs.getString("id_petugas").substring(1);
                String AN=""+(Integer.parseInt(id_petugas)+1);
                String No1="";
                if(AN.length()==1) //Jika id_anggota A00001
                {
                    No1="0000";
                }
                else if(AN.length()==2) //Jika id_anggota A00010
                {
                    No1="000";
                }
                else if(AN.length()==3) //Jika id_anggota A00100
                {
                    No1="00";
                }
                IDPETUGAS.setText("P"+No1+AN);
            }
            else
            {
                IDPETUGAS.setText("P00001");
            }
        }
        catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,e);
                }
    }
    
    //INPUT DATA-----------------------------------------------------------
    private void input_data()
    {
        try
        {
        Connection kon=koneksi.koneksiDb();
        Statement st=kon.createStatement(); 
        // Validasi - pastikan semua kolom terisi
        if (IDPETUGAS.getText().isEmpty() || NAMAPETUGAS.getText().isEmpty() || TEMPAT.getText().isEmpty()
                || TANGGAL.getDate() == null || (JKL.isSelected() && JKP.isSelected()) || ALAMAT.getText().isEmpty()
                || NOTLP.getText().isEmpty() || EMAIL.getText().isEmpty() || JABATAN.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap isi semua kolom sebelum menambahkan data.");
            return; // Jika ada yang kosong, hentikan eksekusi
        }
        
        // Validasi - pastikan nomor telepon hanya berisi angka
        String noTlp = NOTLP.getText();
        if (!noTlp.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Nomor telepon hanya boleh berisi angka.");
            return; // Jika nomor telepon tidak valid, hentikan eksekusi
        }
        
        //Untuk Jenis Kelamin
        String jk= "";
        if(JKL.isSelected())
        {
            jk=JKL.getText();
        }
        else
        {
            jk=JKP.getText();
        }
        
        // Ambil nilai tanggal dari JDateChooser
        Date selectedDate = TANGGAL.getDate();
        

        // Gunakan SimpleDateFormat untuk memformat tanggal
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(selectedDate);

        String sql="INSERT INTO t_petugas values('"+IDPETUGAS.getText()
                +"','"+NAMAPETUGAS.getText()
                +"','"+TEMPAT.getText()
                +"','" + formattedDate
                +"','"+jk
                +"','"+ALAMAT.getText()
                +"','"+NOTLP.getText()
                +"','"+EMAIL.getText()
                +"','"+JABATAN.getText()
                +"')";
        st.execute(sql);
        JOptionPane.showMessageDialog(null, "Data Petugas Berhasil Dimasukkan");
        }
        catch(Exception e)
            {
               JOptionPane.showMessageDialog(null, e);
            }
    }
    
    //Reset Data Setelah Input/Edit/Delete
    public void clear()
    {
           NAMAPETUGAS.setText("");
           TEMPAT.setText("");
           JKL.setSelected(rootPaneCheckingEnabled);
           ALAMAT.setText("");
           NOTLP.setText("");
           EMAIL.setText("");
           JABATAN.setText("");
    }
    
     //Edit Data
    public void update()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String jk="";
            if(JKL.isSelected())
            {
                jk=JKL.getText();
            }
            else
            {
                jk=JKP.getText();
            }
            
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(TANGGAL.getDate());
            
            // Validasi - pastikan nomor telepon hanya berisi angka
            String noTlp = NOTLP.getText();
            if (!noTlp.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Nomor telepon hanya boleh berisi angka.");
            return; // Jika nomor telepon tidak valid, hentikan eksekusi
            }
            
            String sql_update="UPDATE t_petugas SET nama_petugas='"+NAMAPETUGAS.getText()
                    +"',tempat_lahir='"+TEMPAT.getText()
                    +"',tgl_lahir='"+formattedDate
                    +"',jenis_kelamin='"+jk
                    +"',alamat='"+ALAMAT.getText()
                    +"',no_hp='"+NOTLP.getText()
                    +"',email='"+EMAIL.getText()
                    +"',jabatan='"+JABATAN.getText()
                    +"'WHERE id_petugas='"+IDPETUGAS.getText()+"'";
            st.execute(sql_update);
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     //Delete Data Petugas
    private void delete()
    {
        try
        {
          Connection kon=koneksi.koneksiDb();
          Statement st=kon.createStatement();  
          String sql_delete="DELETE from t_petugas WHERE "
                  +"id_petugas='"+IDPETUGAS.getText()+"'";
          st.executeUpdate(sql_delete);
          JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
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

        JK = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        IDPETUGAS = new javax.swing.JTextField();
        NAMAPETUGAS = new javax.swing.JTextField();
        TEMPAT = new javax.swing.JTextField();
        TANGGAL = new com.toedter.calendar.JDateChooser();
        JKL = new javax.swing.JRadioButton();
        JKP = new javax.swing.JRadioButton();
        ALAMAT = new javax.swing.JTextField();
        NOTLP = new javax.swing.JTextField();
        EMAIL = new javax.swing.JTextField();
        JABATAN = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelPetugas = new javax.swing.JTable();
        BKELUAR = new javax.swing.JButton();
        BINPUT = new javax.swing.JButton();
        BEDIT = new javax.swing.JButton();
        BDELETE = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Data Petugas");

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("KELOLA DATA PETUGAS");

        jLabel1.setText("ID PETUGAS");

        jLabel4.setText("NAMA PETUGAS");

        jLabel5.setText("TEMPAT LAHIR");

        jLabel6.setText("TANGGAL LAHIR");

        jLabel7.setText("JENIS KELAMIN");

        jLabel8.setText("ALAMAT");

        jLabel9.setText("NO TLP");

        jLabel10.setText("EMAIL");

        jLabel11.setText("JABATAN");

        IDPETUGAS.setEnabled(false);

        JK.add(JKL);
        JKL.setSelected(true);
        JKL.setText("Laki-Laki");
        JKL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JKLActionPerformed(evt);
            }
        });

        JK.add(JKP);
        JKP.setText("Perempuan");

        TabelPetugas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TabelPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelPetugasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelPetugas);

        BKELUAR.setText("KELUAR");
        BKELUAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BKELUARActionPerformed(evt);
            }
        });

        BINPUT.setText("INPUT");
        BINPUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BINPUTActionPerformed(evt);
            }
        });

        BEDIT.setText("EDIT");
        BEDIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEDITActionPerformed(evt);
            }
        });

        BDELETE.setText("DELETE");
        BDELETE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDELETEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(329, 329, 329))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BKELUAR)
                        .addGap(18, 18, 18)
                        .addComponent(BINPUT)
                        .addGap(18, 18, 18)
                        .addComponent(BEDIT)
                        .addGap(18, 18, 18)
                        .addComponent(BDELETE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel4)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(JKL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JKP))
                            .addComponent(NAMAPETUGAS)
                            .addComponent(TEMPAT)
                            .addComponent(TANGGAL, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ALAMAT)
                            .addComponent(NOTLP, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EMAIL)
                            .addComponent(JABATAN)
                            .addComponent(IDPETUGAS, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IDPETUGAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NAMAPETUGAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(TEMPAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(TANGGAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(JKL)
                            .addComponent(JKP))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(ALAMAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(NOTLP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(EMAIL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(JABATAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BKELUAR)
                    .addComponent(BINPUT)
                    .addComponent(BEDIT)
                    .addComponent(BDELETE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JKLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JKLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JKLActionPerformed

    private void BDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDELETEActionPerformed
        // TODO add your handling code here:
                // TODO add your handling code here:
        int delete = JOptionPane.showOptionDialog(this,
                "Apakah Data Akan Di Hapus?",
                "Hapus Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null,null);
        if(delete==JOptionPane.YES_NO_OPTION)
        {
        delete();
        clear();
        load_data();
        IDOtomatis();
        //Set Enablet INPUT
        BINPUT.setEnabled(true);
        BEDIT.setEnabled(false);
        BDELETE.setEnabled(false);
        
        }
    }//GEN-LAST:event_BDELETEActionPerformed

    private void BKELUARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BKELUARActionPerformed
        // TODO add your handling code here:
     int keluar;
        keluar = JOptionPane.showOptionDialog(this,
                "Keluar dari Aplikasi?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null,null);
        if(keluar==JOptionPane.YES_NO_OPTION)
        {
        new FUtama() .show();
        this.dispose();
        }    
    }//GEN-LAST:event_BKELUARActionPerformed

    private void BINPUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BINPUTActionPerformed
        // TODO add your handling code here:
    int simpan = JOptionPane.showOptionDialog(this,
                "Apakah Data Sudah Benar?",
                "Simpan Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(simpan==JOptionPane.YES_OPTION)
        {
            input_data();
            load_data();
            clear();
            IDOtomatis();
        }    
    }//GEN-LAST:event_BINPUTActionPerformed

    private void TabelPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelPetugasMouseClicked
        // TODO add your handling code here:
        int bar=TabelPetugas.getSelectedRow();
        String a=TabelPetugas.getValueAt(bar, 0).toString();
        String b=TabelPetugas.getValueAt(bar, 1).toString();
        String c=TabelPetugas.getValueAt(bar, 2).toString();
        String d=TabelPetugas.getValueAt(bar, 3).toString();
        String e=TabelPetugas.getValueAt(bar, 4).toString();
        String f=TabelPetugas.getValueAt(bar, 5).toString();
        String g=TabelPetugas.getValueAt(bar, 6).toString();
        String h=TabelPetugas.getValueAt(bar, 7).toString();
        String i=TabelPetugas.getValueAt(bar, 8).toString();
        
        IDPETUGAS.setText(a);
        NAMAPETUGAS.setText(b);
        TEMPAT.setText(c);
        // Set tanggal pada JCalendar
        try {
            Date tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(d);
            TANGGAL.setDate(tanggal);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        //Jenis Kelamin
        if("Laki-Laki".equals(e))
        {
            JKL.setSelected(true);
        }
        else
        {
            JKP.setSelected(true);
        }
        ALAMAT.setText(f);
        NOTLP.setText(g);
        EMAIL.setText(h);
        JABATAN.setText(i);
        //Set Disable Input
        BINPUT.setEnabled(false);
        BEDIT.setEnabled(true);
        BDELETE.setEnabled(true); 
    }//GEN-LAST:event_TabelPetugasMouseClicked

    private void BEDITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEDITActionPerformed
        // TODO add your handling code here:
                // TODO add your handling code here:
         int update = JOptionPane.showOptionDialog(this,
                "Apakah Data Akan di Update?",
                "Update Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(update==JOptionPane.YES_OPTION)
        {
        update();
        clear();
        load_data();
        IDOtomatis();
        //Set Enable INPUT
        BINPUT.setEnabled(false);
        BEDIT.setEnabled(true);
        BDELETE.setEnabled(true); 
        }
    }//GEN-LAST:event_BEDITActionPerformed

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
            java.util.logging.Logger.getLogger(FDataPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FDataPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FDataPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FDataPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FDataPetugas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ALAMAT;
    private javax.swing.JButton BDELETE;
    private javax.swing.JButton BEDIT;
    private javax.swing.JButton BINPUT;
    private javax.swing.JButton BKELUAR;
    private javax.swing.JTextField EMAIL;
    private javax.swing.JTextField IDPETUGAS;
    private javax.swing.JTextField JABATAN;
    private javax.swing.ButtonGroup JK;
    private javax.swing.JRadioButton JKL;
    private javax.swing.JRadioButton JKP;
    private javax.swing.JTextField NAMAPETUGAS;
    private javax.swing.JTextField NOTLP;
    private com.toedter.calendar.JDateChooser TANGGAL;
    private javax.swing.JTextField TEMPAT;
    private javax.swing.JTable TabelPetugas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
