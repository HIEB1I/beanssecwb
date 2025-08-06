
package View;

import Model.User;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField; 
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Login extends javax.swing.JPanel {

 public Frame frame;
    // Added login attempt tracking for security issue #7 (Account Lockout)
    private int loginAttempts = 0;
    private static final int MAX_ATTEMPTS = 3; // Users get 3 attempts max
    
    public Login() {
        initComponents();
        setupSecurityFeatures(); // Added security setup
    }

     // New method to setup security features
    private void setupSecurityFeatures() {
        // Make password field secure (fixes security issue #5 - Unmasked password login field)
        passwordFld.setEchoChar('*'); // This hides what user types with asterisks
        
        // Add Enter key support for better user experience
        KeyListener enterKeyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin(); // Call our secure login method
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        };
        
        usernameFld.addKeyListener(enterKeyListener);
        passwordFld.addKeyListener(enterKeyListener);
        
        clearFields(); // Start with clean fields
    }
    
    // Input validation method (fixes security issue #4 - No Input Validation)
    private boolean validateInput() {
        String username = usernameFld.getText().trim();
        String password = new String(passwordFld.getPassword());
        
        // Check if fields are empty
        if (username.isEmpty()) {
            showError("Error");
            usernameFld.requestFocus();
            return false;
        }
        
        if (password.isEmpty()) {
            showError("Error");
            passwordFld.requestFocus();
            return false;
        }
        
        // EDITED: Check username format (basic security check)
        if (!username.matches("^[a-zA-Z0-9_]{3,20}$")) {
            showError("Username should be 3-20 characters with only letters, numbers, and underscores.");
            usernameFld.requestFocus();
            return false;
        }
        
        // Check for dangerous characters (prevents code injection)
        if (containsDangerousChars(username) || containsDangerousChars(password)) {
            showError("Invalid characters detected.");
            logSecurityWarning("Possible injection attempt detected", username);
            return false;
        }
        
        return true;
    }
    
    // Method to detect potential code injection attempts
    private boolean containsDangerousChars(String input) {
        if (input == null) return false;
        
        // List of suspicious patterns that hackers might use
        String[] dangerousPatterns = {"'", "\"", ";", "--", "/*", "*/", "DROP", "DELETE", "INSERT", "UPDATE", "SELECT"};
        String upperInput = input.toUpperCase();
        
        for (String pattern : dangerousPatterns) {
            if (upperInput.contains(pattern.toUpperCase())) {
                return true; // Found something suspicious!
            }
        }
        return false;
    }
    
    // Main login method with authentication and authorization (fixes security issues #2 and #3)
    private void performLogin() {
        if (!validateInput()) {
            return; // Don't proceed if input is invalid
        }
        
        // Check if user has exceeded login attempts (security issue #7 - Account Lockout)
        if (loginAttempts >= MAX_ATTEMPTS) {
            showError("Too many failed attempts! Please restart the application.");
            loginBtn.setEnabled(false); // Disable login button
            return;
        }
        
        String username = usernameFld.getText().trim();
        String password = new String(passwordFld.getPassword());
        
        try {
            //  Now we actually check credentials against database (fixes security issue #2)
            User user = frame.main.sqlite.authenticateUser(username, password);
            
            if (user != null) {
                // SUCCESS! User provided correct credentials
                loginAttempts = 0; // Reset failed attempts
                clearFields();
                
                // Set current user and navigate based on role (fixes security issue #3 - Authorization)
                frame.setCurrentUser(user);
                frame.navigateToUserHome(user.getRole()); // Goes to appropriate home page based on user role
                
                showSuccess("Welcome back, " + user.getUsername() + "! 🎉");
            } else {
                // FAILED! Wrong username or password
                loginAttempts++;
                clearFields();
                
                int attemptsLeft = MAX_ATTEMPTS - loginAttempts;
                if (attemptsLeft > 0) {
                    showError("Wrong credentials! You have " + attemptsLeft + " attempts remaining.");
                } else {
                    showError("Maximum attempts reached! Application will be locked.");
                    loginBtn.setEnabled(false);
                }
                
                usernameFld.requestFocus(); // Put cursor back in username field
            }
            
        } catch (Exception e) {
            showError("Oops! Something went wrong. Please try again.");
            logSecurityWarning("Login system error", username);
        } finally {
            //Clear password from memory for security
            java.util.Arrays.fill(passwordFld.getPassword(), '\0');
        }
    }
    
    // Helper methods for better user experience
    private void clearFields() {
        usernameFld.setText("");
        passwordFld.setText("");
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Security logging method
    private void logSecurityWarning(String event, String username) {
        try {
            if (frame != null && frame.main != null && frame.main.sqlite != null) {
                java.sql.Timestamp timestamp = new java.sql.Timestamp(new java.util.Date().getTime());
                frame.main.sqlite.addLogs("WARNING", username != null ? username : "UNKNOWN", event, timestamp.toString());
            }
        } catch (Exception e) {
            System.err.println("Failed to log security warning: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        usernameFld = new javax.swing.JTextField();
        passwordFld = new javax.swing.JTextField();
        registerBtn = new javax.swing.JButton();
        loginBtn = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        usernameFld.setBackground(new java.awt.Color(240, 240, 240));
        usernameFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernameFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "USERNAME", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        passwordFld.setBackground(new java.awt.Color(240, 240, 240));
        passwordFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passwordFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        registerBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        registerBtn.setText("REGISTER");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        loginBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        loginBtn.setText("LOGIN");
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(registerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(usernameFld)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordFld, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(usernameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(126, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
         performLogin(); 
    
    }//GEN-LAST:event_loginBtnActionPerformed

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
         clearFields(); // Clear fields before going to registration
        frame.registerNav();
    }//GEN-LAST:event_registerBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loginBtn;
    private javax.swing.JTextField passwordFld;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField usernameFld;
    // End of variables declaration//GEN-END:variables
}
