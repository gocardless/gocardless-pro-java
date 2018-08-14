set -e

java_version=$(java -version 2>&1 >/dev/null | grep 'java version' | awk '{print $3}')

java -version
echo "java version - $java_version"
# Only java 7 needs these deps
if [[ $java_version =~ "1.7" ]]
then
    echo "Installing crypto deps"
    wget "https://bouncycastle.org/download/bcprov-ext-jdk15on-160.jar" -O "${JAVA_HOME}"/jre/lib/ext/bcprov-ext-jdk15on-160.jar
    perl -pi.bak -e 's/^(security\.provider\.)([0-9]+)/$1.($2+1)/ge' /etc/java-7-openjdk/security/java.security
    echo "security.provider.1=org.bouncycastle.jce.provider.BouncyCastleProvider" | tee -a /etc/java-7-openjdk/security/java.security
fi
