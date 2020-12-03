 #!/usr/bin/env bash
java -cp /c/Users/lucas/.m2/repository/com/h2database/h2/1.4.200/h2-1.4.200.jar \
    org.h2.tools.Server \
    -web -webAllowOthers \
    -tcp -tcpAllowOthers \
    -ifNotExists \
    -baseDir /c/Users/lucas/.clickandcollect