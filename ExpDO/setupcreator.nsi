
; This script remembers the directory, 
; has uninstall support and (optionally) installs start menu shortcuts.
;
; It will install the program into a directory that the user selects,

;--------------------------------

; The name of the installer
Name "ExpDO"

XPStyle on

; The file to write
OutFile "setupExpDO.exe"

; The default installation directory
InstallDir $PROGRAMFILES\ExpDO

; Registry key to check for directory (so if you install again, it will 
; overwrite the old one automatically)
InstallDirRegKey HKLM "Software\NSIS_ExpDO" "Install_Dir"

;--------------------------------

; Pages

;Page components
Page directory
Page instfiles

UninstPage uninstConfirm
UninstPage instfiles

;--------------------------------

; The stuff to install
Section "ExpDO (required)"

  SectionIn RO

  ; Set output path to the installation directory.
  SetOutPath $INSTDIR

  ; Put file there
  File /r "dist\saisie-static.jar"
  File /r "jwintab.dll"
  File /r "all.config"
  File /r "ExpExemples"
  File /r "checked.png"
  File /r "unchecked.png"
  File /r "C:\Program Files\Java\jre7"


 
  ; on remet le OUTPATH qui devient le répertoire de lancement
  SetOutPath $INSTDIR

  ; Write the installation path into the registry
  WriteRegStr HKLM SOFTWARE\NSIS_HollerMap "Install_Dir" "$INSTDIR"

  ; Write the uninstall keys for Windows
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ExpDO" "DisplayName" "ExpDO"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ExpDO" "UninstallString" '"$INSTDIR\uninstall.exe"'
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ExpDO" "NoModify" 1
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ExpDO" "NoRepair" 1
  WriteUninstaller "uninstall.exe"

SectionEnd

; Optional section (can be disabled by the user)
Section "Start Menu Shortcuts"


  ; raccourcis lancement
  CreateDirectory "$SMPROGRAMS\ExpDOp"
  CreateShortCut "$SMPROGRAMS\ExpDO\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe" 0
  CreateShortCut "$SMPROGRAMS\ExpDO\ExpDO.lnk" "$INSTDIR\jre7\bin\javaw.exe" "-jar saisie-static.jar"
  CreateShortCut "$SMPROGRAMS\ExpDO\ExpDOConfigure.lnk" "$INSTDIR\jre7\bin\javaw.exe" "-cp saisie-static.jar hollermap.fusion.HollerMapConfigure"

SectionEnd


;--------------------------------

; Uninstaller

Section "Uninstall"

  ; Remove registry keys


  ; Remove files and uninstaller and shortcuts
  RMDir /r "$INSTDIR"

SectionEnd
