# Turo

Old: javapackager -deploy -srcfiles deploy -outdir .. -outfile Turo -appclass turo.Turo -native dmg -title Turo -name Turo
New: 
javapackager -deploy -srcfiles ../workspace/turo/Turo -outdir . -outfile Turo -appclass turo.Turo -native image -title Turo -name Turo -Bruntime=/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/ -Bicon=../workspace/turo/icon.icns
hdiutil convert -format UDZ0 -o Desktop/bundles/Turo-2.0.dmg Desktop/bundles/Turo.dmg
