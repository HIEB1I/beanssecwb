
package View;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField; 
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class Register extends javax.swing.JPanel {

   public Frame frame;
    //Track password strength for real-time validation (fixes security issue #8 - Weak Password Policy)
    private boolean isPasswordStrong = false;
    private String actualPassword = "";
    private String actualConfirmPassword = "";

    public Register() {
        initComponents();
        setupSecurityFeatures(); // EDITED: Added security setup
    }
    
    // Setup security features for registration
    private void setupSecurityFeatures() {
        // Real time password strength checking (security issue #8)
        setupPasswordMasking();
        passwordFld.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                checkPasswordStrength(); // Check if password is strong enough
                checkPasswordsMatch(); // Check if passwords match
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        });
        
        //Check password confirmation 
        confpassFld.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                checkPasswordsMatch(); // Make sure passwords match
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        });
        
        // Add Enter key support
        KeyListener enterKeyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performRegistration();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        };
        
        usernameFld.addKeyListener(enterKeyListener);
        passwordFld.addKeyListener(enterKeyListener);
        confpassFld.addKeyListener(enterKeyListener);
        
        clearFields(); // Start fresh
    }
    
    private void setupPasswordMasking() {
    // Setup main password field
    passwordFld.addKeyListener(new KeyListener() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && actualPassword.length() > 0) {
                actualPassword = actualPassword.substring(0, actualPassword.length() - 1);
            }
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
                return;
            }
            
            if (c != KeyEvent.CHAR_UNDEFINED && c >= 32 && c <= 126) {
                actualPassword += c;
                javax.swing.SwingUtilities.invokeLater(() -> {
                    String maskedText = "•".repeat(actualPassword.length());
                    passwordFld.setText(maskedText);
                    passwordFld.setCaretPosition(maskedText.length());
                    checkPasswordStrength(); // Update strength indicator
                    checkPasswordsMatch(); // Check if passwords match
                });
                e.consume();
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    String maskedText = "•".repeat(actualPassword.length());
                    passwordFld.setText(maskedText);
                    passwordFld.setCaretPosition(maskedText.length());
                    checkPasswordStrength();
                    checkPasswordsMatch();
                });
            }
        }
    });
    
    // Setup confirm password field
    confpassFld.addKeyListener(new KeyListener() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && actualConfirmPassword.length() > 0) {
                actualConfirmPassword = actualConfirmPassword.substring(0, actualConfirmPassword.length() - 1);
            }
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
                return;
            }
            
            if (c != KeyEvent.CHAR_UNDEFINED && c >= 32 && c <= 126) {
                actualConfirmPassword += c;
                javax.swing.SwingUtilities.invokeLater(() -> {
                    String maskedText = "•".repeat(actualConfirmPassword.length());
                    confpassFld.setText(maskedText);
                    confpassFld.setCaretPosition(maskedText.length());
                    checkPasswordsMatch();
                });
                e.consume();
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    String maskedText = "•".repeat(actualConfirmPassword.length());
                    confpassFld.setText(maskedText);
                    confpassFld.setCaretPosition(maskedText.length());
                    checkPasswordsMatch();
                });
            }
        }
    });
}
    // Username validation (fixes security issue #4 - No Input Validation)
    private boolean validateUsername() {
        String username = usernameFld.getText().trim();
        
        if (username.isEmpty()) {
            showError("Username is required! Can't register without one.");
            usernameFld.requestFocus();
            return false;
        }
        
        if (username.length() < 3 || username.length() > 20) {
            showError("Username must be 3-20 characters long.");
            usernameFld.requestFocus();
            return false;
        }
        
        // Only allow safe characters
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            showError("Username can only have letters, numbers, and underscores (_).");
            usernameFld.requestFocus();
            return false;
        }
        
        // Check for hacking attempts
        if (containsDangerousChars(username)) {
            showError("Nice try! Invalid characters detected. 🕵️");
            logSecurityWarning("Possible injection attempt in registration", username);
            return false;
        }
        
        return true;
    }
    
    // Real time password strength validation (fixes security issue #8 - Weak Password Policy)
  private void checkPasswordStrength() {
        String password = actualPassword; // Using getText() since it's JTextField
        
        if (password.length() == 0) {
            passwordFld.setBackground(Color.WHITE); // No color when empty
            isPasswordStrong = false;
            return;
        }
        
        // Check all password requirements (uppercase, lowercase, digit, special character)
        boolean hasUpper = password.matches(".*[A-Z].*");           // Has uppercase letter
        boolean hasLower = password.matches(".*[a-z].*");           // Has lowercase letter  
        boolean hasDigit = password.matches(".*[0-9].*");           // Has number
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"); // Has special character
        boolean isLongEnough = password.length() >= 8;              // At least 8 characters
        
        // Password is strong only if it has ALL requirements
        isPasswordStrong = hasUpper && hasLower && hasDigit && hasSpecial && isLongEnough;
        
        if (isPasswordStrong) {
            passwordFld.setBackground(new Color(200, 255, 200)); // Green = good password! 
        } else if (password.length() > 0) {
            passwordFld.setBackground(new Color(255, 200, 200)); // Red = weak password
        }
    }
    
    
     // Check if password confirmation matches
    private void checkPasswordsMatch() {
        String password = actualPassword;
        String confirmPassword = actualConfirmPassword;
        
        if (confirmPassword.length() == 0) {
            confpassFld.setBackground(Color.WHITE); // No color when empty
            return;
        }
        
        boolean matches = password.equals(confirmPassword);
        
        if (matches && confirmPassword.length() > 0) {
            confpassFld.setBackground(new Color(200, 255, 200)); // Green = passwords match! 
        } else {
            confpassFld.setBackground(new Color(255, 200, 200)); // Red = passwords don't match
        }
    }
    
   // Full password validation (fixes security issue #8)
    private boolean validatePassword() {
        String password = actualPassword;
        String confirmPassword = actualConfirmPassword;
        
        if (password.isEmpty()) {
            showError("Password is required!");
            passwordFld.requestFocus();
            return false;
        }
        
        if (confirmPassword.isEmpty()) {
            showError("Please confirm your password.");
            confpassFld.requestFocus();
            return false;
        }
        
        // Check if password meets strength requirements (security issue #8)
        if (!isPasswordStrong) {
            showError("Password is too weak! It needs:\n" +
                     "• At least 8 characters\n" +
                     "• One UPPERCASE letter (A-Z)\n" +
                     "• One lowercase letter (a-z)\n" +
                     "• One number (0-9)\n" +
                     "• One special character (!@#$%^&*...)");
            passwordFld.requestFocus();
            return false;
        }
        
        // Make sure passwords match
        if (!password.equals(confirmPassword)) {
            showError("Passwords don't match! Please check both fields.");
            confpassFld.requestFocus();
            return false;
        }
        
        // Check for hacking attempts in password
        if (containsDangerousChars(password)) {
            showError("Invalid characters in password. Please use safe characters only.");
            logSecurityWarning("Possible injection attempt in password", usernameFld.getText().trim());
            return false;
        }
        
        return true;
    }
    
    // Detect potential hacking attempts
    private boolean containsDangerousChars(String input) {
        if (input == null) return false;
        
        // List of suspicious patterns hackers might use
        String[] dangerousPatterns = {"'", "\"", ";", "--", "/*", "*/", "DROP", "DELETE", "INSERT", "UPDATE", "SELECT"};
        String upperInput = input.toUpperCase();
        
        for (String pattern : dangerousPatterns) {
            if (upperInput.contains(pattern.toUpperCase())) {
                return true; // Found something fishy!
            }
        }
        return false;
    }
    
     // Main registration method with proper validation
    private void performRegistration() {
        if (!validateUsername() || !validatePassword()) {
            return; // Don't proceed if validation fails
        }
        
        String username = usernameFld.getText().trim();
        String password = actualPassword;
        
        try {
            // Check if username already exists
            if (frame.main.sqlite.userExists(username)) {
                showError("Username already exists! Please choose a different one.");
                usernameFld.requestFocus();
                return;
            }
            
            // Attempt secure registration
            frame.registerAction(username, password, password);
            
            // SUCCESS! New user created
            clearFields();
            showSuccess("Welcome to SECURITY Svcs! 🎉\nYou can now login with your new account.");
            frame.loginNav(); // Go back to login page
            
        } catch (Exception e) {
            showError("Oops! Something went wrong during registration. Please try again.");
            logSecurityWarning("Registration system error", username);
        }
    }
    
    
    // Helper methods
    private void clearFields() {
        usernameFld.setText("");
        passwordFld.setText("");
        confpassFld.setText("");
        
         actualPassword = "";
    actualConfirmPassword = "";
    
        // Reset colors
        usernameFld.setBackground(Color.WHITE);
        passwordFld.setBackground(Color.WHITE);
        confpassFld.setBackground(Color.WHITE);
        
        isPasswordStrong = false;
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Registration Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Registration Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // EDITED: Security logging
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

        registerBtn = new javax.swing.JButton();
        passwordFld = new javax.swing.JTextField();
        usernameFld = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        confpassFld = new javax.swing.JTextField();
        backBtn = new javax.swing.JButton();

        registerBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        registerBtn.setText("REGISTER");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        passwordFld.setBackground(new java.awt.Color(240, 240, 240));
        passwordFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passwordFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        usernameFld.setBackground(new java.awt.Color(240, 240, 240));
        usernameFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernameFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "USERNAME", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        confpassFld.setBackground(new java.awt.Color(240, 240, 240));
        confpassFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        confpassFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        confpassFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "CONFIRM PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        backBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        backBtn.setText("<Back");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(usernameFld)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordFld, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confpassFld, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(200, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backBtn)
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(usernameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confpassFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        frame.registerAction(usernameFld.getText(), passwordFld.getText(), confpassFld.getText());
        frame.loginNav();
    }//GEN-LAST:event_registerBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        frame.loginNav();
    }//GEN-LAST:event_backBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JTextField confpassFld;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField passwordFld;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField usernameFld;
    // End of variables declaration//GEN-END:variables
}
