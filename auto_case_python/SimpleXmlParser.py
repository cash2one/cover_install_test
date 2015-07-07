#-*- encoding: gb2312 -*-
'''
Created on 2013-1-29

@author: houshaolong
'''

import xml.dom.minidom
from xml.dom.minidom import Node
import json

class SimpleXmlParser():
    __doc = None
    def __init__(self,file):
        self.__doc = xml.dom.minidom.parse(file)
        
    def parse(self,list):
        if list == None:
            return
        
        dic = {}
        
        for item in list:
            node = self.__doc.getElementsByTagName(item)[0]
            if node:
                dic[item] = node.childNodes[0].nodeValue
                
        return dic
    def parseArray(self , list):
        if list == None:
            return
        
        dic = {}
        
        for item in list:
	    print item
            nodes = self.__doc.getElementsByTagName(item)
            if nodes:
                nodeArray = [] 
                for element in nodes:
                    nodeArray.append(element.childNodes[0].nodeValue)
                dic[item] = nodeArray                
        return dic
    
if __name__ == '__main__':
#     filePath = './config/pkg_list_conf.xml'
    filePath = './config/downstream_job_conf.xml'
    try:
        file = 'job'
        parser = SimpleXmlParser(filePath)
        dic = parser.parseArray([file])
        dic['hudson_build'] = '123'
        if len(dic):
            print dic[file][0] , dic[file][1]
            print json.dumps(dic)
        else:
            print "no coresponding key-value"
    except Exception , e:
        print "error occured!"
        print e
