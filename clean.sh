#!/bin/bash

#删除文件路径
dir="/soft/oneapi"
# 排除文件
exclude_files="service.sh OneApi-0.0.1-SNAPSHOT.jar clean.sh package.tgz"
#获取文件夹下面所有文件名称
files=$(ls $dir)
# 解压
tar zxvf ${dir}/package.tgz -C ${dir}

#循环遍历删除匹配的文件
for file in ${files[*]}
do 
    sname=$(basename $file)
    if ! [[ "$exclude_files" =~ "$sname" ]]
    then
        echo "file not in $sname"
        rm -rf $dir/$file
    fi 
done

echo "delete success!"