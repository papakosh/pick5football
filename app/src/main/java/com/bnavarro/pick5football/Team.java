package com.bnavarro.pick5football;

/**
 * Created by navman on 8/28/2015.
 */
public class Team {
    private R.drawable teamLogo;
    private String nflTeamCode;
    private String teamName;

    public Team (R.drawable teamLogo, String teamName){
        this.teamLogo=teamLogo;
        this.nflTeamCode=convertNameToNFLCode(teamName);
        this.teamName = teamName;
    }

    public R.drawable getTeamLogo(){
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
