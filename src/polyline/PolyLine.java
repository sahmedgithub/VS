/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyline;

import m.JsonResponse;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import m.Legs;
import m.Routes;
import m.Steps;


/**
 *
 * @author ahmedshabb
 */
public class PolyLine {
    
    public void doStff() throws FileNotFoundException, IOException {
        File f = new File(("C:\\Users\\ahmedshabb\\Documents\\NetBeansProjects\\PolyLine\\src\\m\\newjson.json"));
        
        InputStream in = new FileInputStream(f); 
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
 
        while((line =reader.readLine())!=null){

            stringBuffer.append(line).append("\n");
        }
        
        Gson gson = new Gson();
        JsonResponse js = gson.fromJson(stringBuffer.toString(), JsonResponse.class);
        
         doReal(js);
    }

    private void doReal(JsonResponse a) {
        
        List<Location> allPolylocation = new LinkedList<Location>();
        
        for(Routes r : a.getRoutes()) {
            for(Legs l : r.getLegs()) {
                for(Steps s : l.getSteps()) {
                    List<Location> loc = PolylineDecoder.decodePoly(s.getPolyline().getPoints());
                    for(Location lo : loc) {
                        //System.out.println(lo.getLatitude() + ", " + lo.getLongitude());
                        allPolylocation.add(lo);
                    }
                }
            }
        }
        

        List<Location> distancePolylocation = new LinkedList<Location>();
        
        System.out.println(allPolylocation.size());
        double distance = 0.0;
        int i = 0;
        int nextIndex = 0;
        int e = allPolylocation.size();
        for(Location a2 : allPolylocation) {
            //if(i == e) continue;
            if(i==0) {
                distancePolylocation.add(a2);
            } else {
                Location a1 = allPolylocation.get(nextIndex);
                distance = GeoUtils.getDistance(a1.getLatitude(), a1.getLongitude(), a2.getLatitude(), a2.getLongitude());
                
                if(distance >= 5) {
                   // System.out.println("ffffffffffffffffffffffffffffffffffffffffff " + distance);
                   // System.out.println(a2.getLatitude() + ", " + a2.getLongitude());
                    distance = 0;
                    nextIndex = i;
                    distancePolylocation.add(a2);
                }
            }
            i++;
        }
        
         distancePolylocation.add(allPolylocation.get(allPolylocation.size() - 1));
        
        System.out.println(distancePolylocation.size());
        
        
        for(Location gg : distancePolylocation) {
            System.out.println(gg.getLatitude() + ", " + gg.getLongitude());
        }
        
        
//        List<Location> loc = PolylineDecoder.decodePoly(a.getRoutes()[0].getLegs()[0].getSteps()[0].getPolyline().getPoints());
//        
//        CalculateGreatCircleDistance dc = new CalculateGreatCircleDistance();
//        int i = 0;
//        for(Location lo : loc) {
//            System.out.println( " in " + (i++) + " " + lo.getLatitude() + ", " + lo.getLongitude());
//           
//        }
//        
//        Location a1 = loc.get(0);
//        Location a2 = loc.get(2);
//        
//        
//        System.out.println(" D " + (dc.calculateDistance(a1.getLatitude(), a1.getLongitude(), a2.getLatitude(), a2.getLongitude()) /  1609.344)  );
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        PolyLine p = new PolyLine();
        try {
            p.doStff();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PolyLine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
