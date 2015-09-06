package com.bnavarro.pick5football;

/**
 * Created by navman on 8/28/2015.
 */
public class Team {
    private int teamLogo;
    private String nflTeamCode;
    private String teamName;

    public Team (String teamName){
        this.nflTeamCode=convertNameToNFLCode(teamName);
        this.teamName = teamName;
        setTeamLogo();
    }

    private void setTeamLogo (){
        switch (teamName) {
            case "Arizona Cardinals":
                teamLogo = R.drawable.arizona_cardinals_logo;
                break;
            case "Detroit Lions":
                teamLogo = R.drawable.detroit_lions_logo;
                break;
            case "Chicago Bears":
                teamLogo = R.drawable.chicago_bears_logo;
                break;
            case "Dallas Cowboys":
                teamLogo = R.drawable.dallas_cowboys_logo;
                break;
            case "Philadelphia Eagles":
                teamLogo = R.drawable.philadelphia_eagles_logo;
                break;
            case "San Francisco 49ers":
                teamLogo = R.drawable.sanfrancisco_49ers_logo;
                break;
            case "Seattle Seahawks":
                teamLogo = R.drawable.seattle_seahawks_logo;
                break;
            case "Baltimore Ravens":
                teamLogo = R.drawable.baltimore_ravens_logo;
                break;
            case "San Diego Chargers":
                teamLogo = R.drawable.sandiego_chargers_logo;
                break;
            case "Buffalo Bills":
                teamLogo = R.drawable.buffalo_bills_logo;
                break;
            case "Cleveland Browns":
                teamLogo = R.drawable.cleveland_browns_logo;
                break;
            case "Houston Texans":
                teamLogo = R.drawable.houston_texans_logo;
                break;
            case "Tennessee Titans":
                teamLogo = R.drawable.tennessee_titans_logo;
                break;
            case "Indianapolis Colts":
                teamLogo = R.drawable.indianapolis_colts_logo;
                break;
            case "Washington Redskins":
                teamLogo = R.drawable.washington_redskins_logo;
                break;
            case "Jacksonville Jaguars":
                teamLogo = R.drawable.jacksonville_jaguars_logo;
                break;
            case "NY Giants":
                teamLogo = R.drawable.newyork_giants_logo;
                break;
            case "Minnesota Vikings":
                teamLogo = R.drawable.minnesota_vikings_logo;
                break;
            case "Carolina Panthers":
                teamLogo = R.drawable.carolina_panthers_logo;
                break;
            case "Pittsburgh Steelers":
                teamLogo = R.drawable.pittsburgh_steelers_logo;
                break;
            case "New Orleans Saints":
                teamLogo = R.drawable.neworleans_saints_logo;
                break;
            case "St. Louis Rams":
                teamLogo = R.drawable.stlouis_rams_logo;
                break;
            case "Oakland Raiders":
                teamLogo = R.drawable.oakland_raiders_logo;
                break;
            case "Tampa Bay Buccaneers":
                teamLogo = R.drawable.tampabay_buccaneers_logo;
                break;
            case "Cincinnati Bengals":
                teamLogo = R.drawable.cincinnati_bengals_logo;
                break;
            case "Atlanta Falcons":
                teamLogo = R.drawable.atlanta_falcons_logo;
                break;
            case "Green Bay Packers":
                teamLogo = R.drawable.greenbay_packers_logo;
                break;
            case "New England Patriots":
                teamLogo = R.drawable.newengland_patriots_logo;
                break;
            case "Kansas City Chiefs":
                teamLogo = R.drawable.kansascity_chiefs_logo;
                break;
            case "Denver Broncos":
                teamLogo = R.drawable.denver_broncos_logo;
                break;
            case "NY Jets":
                teamLogo = R.drawable.newyork_jets_logo;
                break;
            case "Miami Dolphins":
                teamLogo = R.drawable.miami_dolphins_logo;
                break;
            default:
                throw new IllegalArgumentException("Invalid team name: " + teamName);
        }
    }
    public int getTeamLogo(){
        return teamLogo;
    }

    public String getNFLTeamCode ()
    {
        return nflTeamCode;
    }

    public String getTeamName(){
        return teamName;
    }

    public static String convertNameToNFLCode (String teamName){

        String sign;
        switch (teamName){
            case "Arizona Cardinals":
                sign = "ARI";
                break;
            case "Detroit Lions":
                sign = "DET";
                break;
            case "Chicago Bears":
                sign = "CHI";
                break;
            case "Dallas Cowboys":
                sign = "DAL";
                break;
            case "Philadelphia Eagles":
                sign = "PHI";
                break;
            case "San Francisco 49ers":
                sign = "SF";
                break;
            case "Seattle Seahawks":
                sign = "SEA";
                break;
            case "Baltimore Ravens":
                sign = "BAL";
                break;
            case "San Diego Chargers":
                sign = "SD";
                break;
            case "Buffalo Bills":
                sign = "BUF";
                break;
            case "Cleveland Browns":
                sign = "CLE";
                break;
            case "Houston Texans":
                sign = "HOU";
                break;
            case "Tennessee Titans":
                sign = "TEN";
                break;
            case "Indianapolis Colts":
                sign = "IND";
                break;
            case "Washington Redskins":
                sign = "WAS";
                break;
            case "Jacksonville Jaguars":
                sign = "JAC";
                break;
            case "NY Giants":
                sign = "NYG";
                break;
            case "Minnesota Vikings":
                sign = "MIN";
                break;
            case "Carolina Panthers":
                sign="CAR";
                break;
            case "Pittsburgh Steelers":
                sign="PIT";
                break;
            case "New Orleans Saints":
                sign="NO";
                break;
            case "St. Louis Rams":
                sign="STL";
                break;
            case "Oakland Raiders":
                sign="OAK";
                break;
            case "Tampa Bay Buccaneers":
                sign="TB";
                break;
            case "Cincinnati Bengals":
                sign="CIN";
                break;
            case "Atlanta Falcons":
                sign="ATL";
                break;
            case "Green Bay Packers":
                sign = "GB";
                break;
            case "New England Patriots":
                sign="NE";
                break;
            case "Kansas City Chiefs":
                sign="KC";
                break;
            case "Denver Broncos":
                sign="DEN";
                break;
            case "NY Jets":
                sign="NYJ";
                break;
            case "Miami Dolphins":
                sign="MIA";
                break;
            default:
                throw new IllegalArgumentException("Invalid team name: " + teamName);


        }
        return sign;
    }

}
