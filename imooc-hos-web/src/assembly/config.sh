#!/bin/sh
classpath="./conf:./static"
for file in lib/*.jar
do
  if [[ ! -f "$file" ]]
  then
      continue
  fi
  classpath="$classpath:$file"
done
echo $classpath
JAVA_OPTS="-Duser.timezone=GMT+08"

export classpath
export JAVA_OPTS