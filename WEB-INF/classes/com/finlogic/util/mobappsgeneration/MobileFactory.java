/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.mobappsgeneration;

/**
 *
 * @author njuser
 */
public enum MobileFactory
{

    ANDROID
    {

        @Override
        public String toString()
        {
            return "android";
        }
    },
    BLACKBERRY
    {

        @Override
        public String toString()
        {
            return "blackberry";
        }
    },
    IOS
    {

        @Override
        public String toString()
        {
            return "ios";
        }
    },
    SYMBIAN
    {

        @Override
        public String toString()
        {
            return "symbian";
        }
    };
}
