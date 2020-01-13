package com.bnavarro.pick5football;

public class Team {
    private String name;

    public String getName(){
        return name;
    }
    public Team (String name){
        this.name = name;
    }


    /** Returns a team's logo when the name matches below. If a name is invalid or null, an
     * exception is thrown
     *
     * @return resource id for image
     */
    public int getLogo (){
        CommonUtils.validateNotNull(name, "Missing team name");
        switch (name) {
            case "Arizona Cardinals":
                return R.drawable.arizona_cardinals_logo;
            case "Detroit Lions":
                return R.drawable.detroit_lions_logo;
            case "Chicago Bears":
                return R.drawable.chicago_bears_logo;
            case "Dallas Cowboys":
                return R.drawable.dallas_cowboys_logo;
            case "Philadelphia Eagles":
                return R.drawable.philadelphia_eagles_logo;
            case "San Francisco 49ers":
                return R.drawable.sanfrancisco_49ers_logo;
            case "Seattle Seahawks":
                return R.drawable.seattle_seahawks_logo;
            case "Baltimore Ravens":
                return R.drawable.baltimore_ravens_logo;
            case "Los Angeles Chargers":
                return R.drawable.losangeles_chargers_logo;
            case "Buffalo Bills":
                return R.drawable.buffalo_bills_logo;
            case "Cleveland Browns":
                return R.drawable.cleveland_browns_logo;
            case "Houston Texans":
                return R.drawable.houston_texans_logo;
            case "Tennessee Titans":
                return R.drawable.tennessee_titans_logo;
            case "Indianapolis Colts":
                return R.drawable.indianapolis_colts_logo;
            case "Washington Redskins":
                return R.drawable.washington_redskins_logo;
            case "Jacksonville Jaguars":
                return R.drawable.jacksonville_jaguars_logo;
            case "New York Giants":
                return R.drawable.newyork_giants_logo;
            case "Minnesota Vikings":
                return R.drawable.minnesota_vikings_logo;
            case "Carolina Panthers":
                return  R.drawable.carolina_panthers_logo;
            case "Pittsburgh Steelers":
                return  R.drawable.pittsburgh_steelers_logo;
            case "New Orleans Saints":
                return  R.drawable.neworleans_saints_logo;
            case "Los Angeles Rams":
                return  R.drawable.losangeles_rams_logo;
            case "Oakland Raiders":
                return  R.drawable.oakland_raiders_logo;
            case "Tampa Bay Buccaneers":
                return  R.drawable.tampabay_buccaneers_logo;
            case "Cincinnati Bengals":
                return  R.drawable.cincinnati_bengals_logo;
            case "Atlanta Falcons":
                return  R.drawable.atlanta_falcons_logo;
                
            case "Green Bay Packers":
                return  R.drawable.greenbay_packers_logo;
                
            case "New England Patriots":
                return  R.drawable.newengland_patriots_logo;
                
            case "Kansas City Chiefs":
                return  R.drawable.kansascity_chiefs_logo;
                
            case "Denver Broncos":
                return  R.drawable.denver_broncos_logo;
                
            case "New York Jets":
                return  R.drawable.newyork_jets_logo;
                
            case "Miami Dolphins":
                return  R.drawable.miami_dolphins_logo;

            default:
                throw new IllegalArgumentException("Invalid team name: " + name);
        }
    }

    /** Returns the nfl code for a team when the name matches below. If a name is invalid or null,
     * an exception is thrown
     *
     * @return a two or three letter code identifying a team
     */
    public String getNFLCode (){
        CommonUtils.validateNotNull(name, "Missing team name");
        switch (name){
            case "Arizona Cardinals":
                return "ARI";
            case "Detroit Lions":
                return "DET";
            case "Chicago Bears":
                return "CHI";
            case "Dallas Cowboys":
                return "DAL";
            case "Philadelphia Eagles":
                return "PHI";
            case "San Francisco 49ers":
                return "SF";
            case "Seattle Seahawks":
                return "SEA";
            case "Baltimore Ravens":
                return "BAL";
            case "Los Angeles Chargers":
                return "LAC";
            case "Buffalo Bills":
                return "BUF";
            case "Cleveland Browns":
                return "CLE";
            case "Houston Texans":
                return "HOU";
            case "Tennessee Titans":
                return "TEN";
            case "Indianapolis Colts":
                return "IND";
            case "Washington Redskins":
                return "WAS";
            case "Jacksonville Jaguars":
                return "JAC";
            case "New York Giants":
                return "NYG";
            case "Minnesota Vikings":
                return "MIN";
            case "Carolina Panthers":
                return "CAR";
            case "Pittsburgh Steelers":
                return "PIT";
            case "New Orleans Saints":
                return "NO";
            case "Los Angeles Rams":
                return "LAR";
            case "Oakland Raiders":
                return "OAK";
            case "Tampa Bay Buccaneers":
                return "TB";
            case "Cincinnati Bengals":
                return "CIN";
            case "Atlanta Falcons":
                return "ATL";
            case "Green Bay Packers":
                return "GB";
            case "New England Patriots":
                return "NE";
            case "Kansas City Chiefs":
                return "KC";
            case "Denver Broncos":
                return "DEN";
            case "New York Jets":
                return "NYJ";
            case "Miami Dolphins":
                return "MIA";
            default:
                throw new IllegalArgumentException("Invalid team name: " + name);
        }
    }
}
