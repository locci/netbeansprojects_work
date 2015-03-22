<%-- 
    Document   : command
    Created on : Aug 28, 2014, 11:59:45 AM
    Author     : alexandre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body bgcolor="#E6E6FA">      
    <div>
    <style scoped>

        .button-secondary {
            color: white;
            border-radius: 4px;
            text-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
            background: rgb(30, 144, 255); /* this is a light blue */
            min-width: 200px;
            max-width: 200px;
            font: verdana;
        }        

    </style> 
    
    <table border="0" align="center">
        <thead>
            <tr>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <form name="load" action="LoadFile" method="GET" target="frame_a">          
                      <button type="submit" value="BlackBox Requeriment" name="btOne" class="button-secondary pure-button">Requeriment BlackBox</button>
                    </form> 
                </td>
            </tr>
            <tr>
                <td>
                    <form name="load" action="LoadFileCondition" method="GET" target="frame_a">
                      <button type="submit" value="Condition Coverage Requeriment" name="btTwo" class="button-secondary pure-button">Requeriment Condition Coverage</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td>
                    <form name="load" action="LoadFileDecision" method="GET" target="frame_a">
                       <button type="submit" value="Decision Coverage Requeriment" name="btThree" class="button-secondary pure-button">Requeriment Decision Coverage</button>
                    </form> 
                </td>
            </tr>
            <tr>
                <td>
                    <form name="load" action="LoadFileError" method="GET" target="frame_a">
                        <button type="submit" value="Error Requeriment" name="btFour" class="button-secondary pure-button">Requeriment Error</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td>
                    <form name="load" action="LoadMCDC" method="GET" target="frame_a">
                        <button type="submit" value="MC/DC Requeriment" name="btFive" class="button-secondary pure-button">Requeriment MC/DC</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td>
                    <form name="load" action="" method="GET" target="frame_a">
                        <button type="submit" value="RC/DC Requeriment" name="btSix" class="button-secondary pure-button">Requeriment RC/DC</button>
                    </form> 
                </td>
            </tr>            
        </tbody>
    </table>       
        
    </div>
    </body>
</html>
