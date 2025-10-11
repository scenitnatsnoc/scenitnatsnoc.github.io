import sys
# Configure the necessary Python module imports for dashboard components
import dash
import dash_leaflet as dl
from dash import dcc
from dash import html
import plotly.express as px
from dash import dash_table
from dash.dependencies import Input, Output, State
from dash_extensions.enrich import DashProxy
import base64

# leaflet dependencies
import dash_leaflet.express as dlx
from dash_extensions.javascript import arrow_function, assign

# Configure OS routines and dotenv to access our secrets from .env file
import os
from dotenv import load_dotenv

# importing googlemaps libs
import googlemaps

# Configure the plotting routines
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

#json libs to load and dump
import json
from json import loads, dumps

# Incorporate css
external_stylesheets = ['https://codepen.io/chriddyp/pen/bWLwgP.css']

# importing custom Python CRUD module
from RmasCrudClass import QuantRMA

# Load environment variables from .env file
load_dotenv()

# Access our dotenv variables
db_user = os.getenv("USERNAME")
db_pass = os.getenv("PASSWORD")
g_api_key = os.getenv("G_KEY")

if g_api_key:
    print("Google Maps API key was loaded successfully.")
    # Use g_api_key in your application
else:
    print("Google Maps API key G_KEY not found. Please check your .env file and define it there.")

###########################
# Data Manipulation / Model
###########################
username = db_user      # not using right now
password = db_pass      # not using right now

# Connect to database via CRUD Module
db = QuantRMA(username, password)

# Importing our States information with
# rma counts added from the DataFrame of counts below
# value_counts = dff['state'].value_counts().to_frame('count').reset_index().rename(columns={'index': 'state'})
us_states_file = ".\\us-states.json"
with open(us_states_file, 'r') as file:
    print("Opening out file for our heat map")
    states_data = json.load(file)

# function to retrieve coordinates for state with google maps api
def getCoordinatesGmaps(myState):
    gmaps = googlemaps.Client(key=g_api_key)
    myList = []
    myList.append(gmaps.geocode(myState)[0]['geometry']['location']['lng'])
    myList.append(gmaps.geocode(myState)[0]['geometry']['location']['lat'])
    return myList

# class read method must support return of list object and accept projection json input
# sending the read method an empty document requests all documents be returned
df = pd.DataFrame.from_records(db.read({}))

# MongoDB v5+ is going to return the '_id' column and that is going to have an 
# invlaid object type of 'ObjectID' - which will cause the data_table to crash - so we remove
# it in the dataframe here. The df.drop command allows us to drop the column. If we do not set
# inplace=True - it will reeturn a new dataframe that does not contain the dropped column(s)
df.drop(columns=['_id'],inplace=True)


## Debug
# print(len(df.to_dict(orient='records')))
# print(df.columns)

def get_info(feature=None):
    header = [html.H4("RMAs Choropleth Map")]
    if not feature:
        return header + [html.P("Hover over a state")]
    return header + [
        html.B(feature["properties"]["name"]),
        html.Br(),
        "{} RMAs per state".format(feature["properties"]["count"]),
    ]

classes = [650, 700, 750, 800, 850, 900, 950, 1000]
colorscale = ["#FFEDA0", "#FED976", "#FEB24C", "#FD8D3C", "#FC4E2A", "#E31A1C", "#BD0026", "#800026"]
style = dict(weight=2, opacity=1, color="white", dashArray="3", fillOpacity=0.7)


# Create colorbar.
ctg = [
    "{}+".format(
        cls,
    )
    for i, cls in enumerate(classes[:-1])
] + ["{}+".format(classes[-1])]
colorbar = dlx.categorical_colorbar(categories=ctg, colorscale=colorscale, width=300, height=30, position="bottomleft")


# Geojson rendering logic, must be JavaScript as it is executed in clientside.
style_handle = assign("""function(feature, context){
    const {classes, colorscale, style, colorProp} = context.hideout;  // get props from hideout
    const value = feature.properties[colorProp];  // get value the determines the color
    for (let i = 0; i < classes.length; ++i) {
        // if less then 100 pick the color below
        if (value < 100) {
            style.fillColor = "#faf9f7";
        }
        if (value > classes[i]) {
            style.fillColor = colorscale[i];  // set the fill color according to the class
        }
    }
    return style;
}""")

# Create geojson.
geojson = dl.GeoJSON(
    data=states_data,    # setting our data from imported json earlier
    style=style_handle,  # how to style each polygon
    zoomToBounds=False,  # when true, zooms to bounds when data changes (e.g. on load)
    zoomToBoundsOnClick=False,  # when true, zooms to bounds of feature (e.g. polygon) on click
    hoverStyle=arrow_function(dict(weight=5, color="#666", dashArray="")),  # style applied on hover
    hideout=dict(colorscale=colorscale, classes=classes, style=style, colorProp="count"),
    id="geojson",
)

# Create info control.
info = html.Div(
    children=get_info(),
    id="info",
    className="info",
    style={"position": "absolute", "top": "10px", "right": "10px", "zIndex": "1000"},
)

#########################
# Dashboard Layout / View
#########################
# Create app.
app = DashProxy(prevent_initial_callbacks=True)

#Add in QuantigrationRMA's logo
image_filename = 'QuantigrationRMA.png' 
encoded_image = base64.b64encode(open(image_filename, 'rb').read())

app.layout = html.Div([
#    html.Div(id='hidden-div', style={'display':'none'}),
    html.Div(
        id='image-logo-id',
        style={"textAlign": "center"},
        children=[
        html.Img(src='data:image/png;base64,{}'.format(encoded_image.decode()), 
                 alt="Quantigration RMA", width="280", height="260")
                ]
        ),
    html.Div(
        id='title-id',
        style={"textAlign": "center"},
        children=[
        html.Div(children = [html.H4('Quantigration RMA Dashboard')]),
        html.Div(children = [html.H4('CS499 Database Enhancements')]),
                ]
        ),
    html.Div(
        id='choroplethmap-id',    
        children=[
            dl.Map(
                style={'width': '100%', 'height': '50vh', 'margin': "auto", "display": "block"},
                center=[39, -98], 
                zoom=4,
                children=[
                    dl.TileLayer(), 
                    geojson, 
                    colorbar, 
                    info
                    ], 

            ),
        ]
    ),
    html.Hr(),
    html.Div(className='row',
             style={'display' : 'flex'},
             children=[
                        html.Div(
                        id='button-id',
                        className='col s12 m6',
                        style={
                              'display' : 'inline', 'width': '300px',
                              'margin-left': '5px', 'margin-right': 'auto'
                              },
                        children=[
                            html.Div(
                                    dcc.Dropdown(
                                    ['Initiated', 'Complete', 'Pending', 'All'],
                                    'All',
                                    id='filter-type')
                                    )
                                ]
                                ),
                        html.Div(
                        id='identifier-id',
                        className='col s12 m6',
                        style={'textAlign':"right"},
                        children =[
                                    html.Div('Konstantin Dobikov', style={'color': 'black', 'fontSize': 14})
                                  ]
                                ),
                       ]
                ),
        
    html.Hr(),

    dash_table.DataTable(id='datatable-id',
                         data=df.to_dict('records'),
                        # Custom columns  
                         columns=[
                                    {"name": 'RMA ID', "id": 'rmaid', "deletable": False, "selectable": True},
                                    {"name": 'Progress', "id": 'step', "deletable": False, "selectable": True},
                                    {"name": 'Status', "id": 'status', "deletable": False, "selectable": True},
                                    {"name": 'Reason', "id": 'reason', "deletable": False, "selectable": True},
                                    {"name": 'SKU', "id": 'sku', "deletable": False, "selectable": True},
                                    {"name": 'Description', "id": 'description', "deletable": False, "selectable": True},
                                    {"name": 'State', "id": 'state', "deletable": False, "selectable": True},
                                    {"name": 'ZIP', "id": 'zipCode', "deletable": False, "selectable": True}
                                ],
                         editable=False,
                         filter_action="native",
                         sort_action="native",
                         sort_mode="multi",
                         column_selectable="multi",
                         row_deletable=False,
                         row_selectable = "single",
                         selected_columns=[],
                         selected_rows=[0],
                         page_action="native",
                         page_current=0,
                         page_size=10,
                         # Table styles
                         style_header={
                                    'backgroundColor': 'rgb(30, 30, 30)',
                                    'color': 'white'
                                     },
                         style_data={
                                    'backgroundColor': 'rgb(50, 50, 50)',
                                    'color': 'white'
                                     },
                         style_table={ 
                                    'overflowX': 'auto',
                                    'margin-left': 'auto', 'margin-right': 'auto', 'align': "left"
                                     },
                         style_cell={
                                    'minWidth': '250px', 'width': '250px', 'maxWidth': '250px',
                                    'overflow': 'hidden',
                                    'textOverflow': 'ellipsis',
                                     }

                        ),
    html.Br(),
    html.Hr(),
# pie chart and geolocation chart are side-by-side
    html.Div(className='row',
         style={'display' : 'flex'}, 
             children=[
        html.Div(
            id='graph-id',
            className='col s12 m6',
            style={
                  'display' : 'inline', 'width': '1000px',
                  'margin-left': 'auto', 'margin-right': 'auto', 'align': "center"
                  },
            ),
        html.Div(
            id='map-id',
            className='col s12 m6',
            style={
                  'display' : 'inline', 'width': '1000px',
                  'margin-left': 'auto', 'margin-right': 'auto', 'align': "center"
                  },
            )

        ])
])

#############################################
# Interaction Between Components / Controller
#############################################

    
@app.callback(Output('datatable-id','data'),
              [Input('filter-type', 'value'),
     Input('datatable-id', "derived_virtual_selected_rows")])
def update_dashboard(filter_type, index):
# custom filters to get reqested data
        # Initiated RMAs
        if filter_type == "Initiated":
            df = pd.DataFrame.from_records(db.read(
                {
                    "status":{"$in":["Initiated"]},
                     }
            ))
        # Completed RMAs
        elif filter_type == "Complete":
            df = pd.DataFrame.from_records(db.read(
               {
                     "status":{"$in":["Complete"]},
                }
            ))
        # Pending RMAs
        elif filter_type == "Pending":
            df = pd.DataFrame.from_records(db.read(
                {
                    "status":{"$in":["Pending"]}, 
                }
            ))
        else:
            df = pd.DataFrame.from_records(db.read({}))
            
        # Cleanup Mongo _id field
        df.drop(columns=['_id'],inplace=True)
        data=df.to_dict('records')
        return data

# callback for our PIE chart
@app.callback(
   Output('graph-id', "children"),
   [Input('filter-type', 'value'),
    Input('datatable-id', "derived_virtual_data")])
def update_graphs(filter_type, viewData):
    if viewData is None:
        df = pd.DataFrame.from_records(db.read({}))
    else:
        df = pd.DataFrame.from_dict(viewData)
    value_counts = df['sku'].value_counts().to_frame('count').reset_index().rename(columns={'index': 'sku'})
    # print(value_counts)
    if filter_type == 'All' or filter_type is None:
        value_counts.loc[ value_counts['count'] < 300, 'sku'] = 'Other SKUs'
    return [
        dcc.Graph(            
            figure = px.pie(value_counts, values='count', names='sku', title='SKUs')
        )    
    ]
    
#This callback will highlight a cell on the data table when the user selects it
@app.callback(
    Output('datatable-id', 'style_data_conditional'),
    [Input('datatable-id', 'selected_columns')]
)
def update_styles(selected_columns):
    return [{
        'if': { 'column_id': i },
        'background_color': '#660066' # Changed from '#D2F3FF' for dark theme colors
    } for i in selected_columns]


# This callback will update the geo-location chart for the selected data entry
# derived_virtual_data will be the set of data available from the datatable in the form of 
# a dictionary.
# derived_virtual_selected_rows will be the selected row(s) in the table in the form of
# a list. For this application, we are only permitting single row selection so there is only
# one value in the list.
# The iloc method allows for a row, column notation to pull data from the datatable

@app.callback(Output("info", "children"), 
            [Input("geojson", "hoverData"),
             ])
def info_hover(feature):
    return get_info(feature)

@app.callback(
    Output('map-id', "children"),
    [Input('datatable-id', "derived_virtual_data"), 
    Input('datatable-id', "derived_virtual_selected_rows"),
     ],
    )
def update_map(viewData, index):
    
    if viewData is None:
        return
    elif index is None:
        return
    
    dff = pd.DataFrame.from_dict(viewData)
    
    # Because we only allow single row selection, the list can be converted to a row index here
    # plus fixing the errors when index for selected items are higher then current results
    if index is None or len(index) == 0:
        row = 0
    else:
        row = index[0]
    
    # getting coordinates
    coord = getCoordinatesGmaps(dff.iloc[row, 11])
    position1=[coord[1],coord[0]]
    return [
        dl.Map(style={'width': '1000px', 'height': '500px'}, 
               center=[coord[1],coord[0]], # let's center around our selected row location
               zoom=5,           
               children=[
                    dl.TileLayer(id="base-layer-id"),
                    # Marker with tool tip and popup
                    # Column 9 and 10 define name of the customer
                    # Column 12 defines street of the customer
                    # Column 13 defines telephone number of the customer
                    # Column 14 defines ZipCode of the customer
                    dl.LayerGroup(    
                        id="marker-layer",
                        children=[
                            dl.Marker(
                                id="my-marker", 
                                position=[coord[1],coord[0]],                        
                                children=[
                                    dl.Tooltip(dff.iloc[row,4]),
                                    dl.Popup(
                                        [
                                        html.H4("Cutomer info"), 
                                        html.P("Name: {} {}".format(dff.iloc[row,9], dff.iloc[row, 10])),
                                        html.P("Street: {}".format(dff.iloc[row, 12])),
                                        html.P("Tel: {}".format(dff.iloc[row,13])),
                                        html.P("Zip: {}".format(dff.iloc[row,14]))
                                        ])
                                ])
                        ]
                    ),
                ]),
    ]
if __name__ == "__main__":
    app.run(port=8087, debug=False)