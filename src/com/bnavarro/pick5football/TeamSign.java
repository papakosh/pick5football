package com.bnavarro.pick5football;

/** Matches a team name to a two or three letter team sign 
 * (i.e. ARI for Arizona Cardinals, DET for Detroit Lions)
 * 
 * @author brian navarro
 *
 */
public class TeamSign
{

	public static String getSignFromTeamName (String teamName){
	
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
			case "New York Giants":
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
			case "St Louis Rams":
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
			case "New York Jets":
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
