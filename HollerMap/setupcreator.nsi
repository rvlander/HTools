
; This script remembers the directory, 
; has uninstall support and (optionally) installs start menu shortcuts.
;
; It will install the program into a directory that the user selects,

;--------------------------------

; The name of the installer
Name "HollerMap"

XPStyle on

; The file to write
OutFile "setupHollerMap.exe"

; The default installation directory
InstallDir $PROGRAMFILES\HollerMap

; Registry key to check for directory (so if you install again, it will 
; overwrite the old one automatically)
InstallDirRegKey HKLM "Software\NSIS_HollerMap" "Install_Dir"

;--------------------------------

; Pages

;Page components
Page directory
Page instfiles

UninstPage uninstConfirm
UninstPage instfiles

;--------------------------------

; The stuff to install
Section "HollerMap (required)"

  SectionIn RO

  ; Set output path to the installation directory.
  SetOutPath $INSTDIR

  ; Put file there
  File /r "dist\saisie-static.jar"
  File /r "jwintab.dll"
  File /r "all.config"
  File /r "C:\Program Files\Java\jre7"


 
  ; on remet le OUTPATH qui devient le répertoire de lancement
  SetOutPath $INSTDIR

  ; Write the installation path into the registry
  WriteRegStr HKLM SOFTWARE\NSIS_HollerMap "Install_Dir" "$INSTDIR"

  ; Write the uninstall keys for Windows
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\HollerMap" "DisplayName" "HollerMap"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\HollerMap" "UninstallString" '"$INSTDIR\uninstall.exe"'
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\HollerMap" "NoModify" 1
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\HollerMap" "NoRepair" 1
  WriteUninstaller "uninstall.exe"

SectionEnd

; Optional section (can be disabled by the user)
Section "Start Menu Shortcuts"


  ; raccourcis lancement
  CreateDirectory "$SMPROGRAMS\HollerMap"
  CreateShortCut "$SMPROGRAMS\HollerMap\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe" 0
  CreateShortCut "$SMPROGRAMS\HollerMap\HollerMap.lnk" "$INSTDIR\jre7\bin\javaw.exe" "-jar saisie-static.jar"
  CreateShortCut "$SMPROGRAMS\HollerMap\HollerMapConfigure.lnk" "$INSTDIR\jre7\bin\javaw.exe" "-cp saisie-static.jar hollermap.fusion.HollerMapConfigure"

SectionEnd


;--------------------------------

; Uninstaller

Section "Uninstall"

  ; Remove registry keys


  ; Remove files and uninstaller and shortcuts
  RMDir /r "$INSTDIR"

SectionEnd
