"use strict";

var testData = {
states: [
    {
        "id": 0,
        "fields": [

        ]
    },
    {
        "id": 6,
        "fields": [
            {
                "name": "string1",
                "value": "twofour"
            },
            {
                "name": "string2",
                "value": "two"
            },
            {
                "name": "integer1",
                "value": "6"
            },
            {
                "name": "integer2",
                "value": "5"
            }
        ]
    },
    {
        "id": 2,
        "fields": [
            {
                "name": "string1",
                "value": "two"
            },
            {
                "name": "string2",
                "value": "four"
            },
            {
                "name": "integer1",
                "value": "2"
            },
            {
                "name": "integer2",
                "value": "4"
            }
        ]
    },
    {
        "id": 1,
        "fields": [
            {
                "name": "string1"
            },
            {
                "name": "string2"
            },
            {
                "name": "integer1",
                "value": "0"
            },
            {
                "name": "integer2",
                "value": "0"
            }
        ]
    },
    {
        "id": 3,
        "fields": [
            {
                "name": "string1",
                "value": "two"
            },
            {
                "name": "string2",
                "value": "four"
            },
            {
                "name": "integer1",
                "value": "2"
            },
            {
                "name": "integer2",
                "value": "4"
            }
        ]
    },
    {
        "id": 5,
        "fields": [
            {
                "name": "string1",
                "value": "two"
            },
            {
                "name": "string2",
                "value": "four"
            },
            {
                "name": "integer1",
                "value": "6"
            },
            {
                "name": "integer2",
                "value": "5"
            }
        ]
    },
    {
        "id": 4,
        "fields": [
            {
                "name": "string1",
                "value": "two"
            },
            {
                "name": "string2",
                "value": "four"
            },
            {
                "name": "integer1",
                "value": "6"
            },
            {
                "name": "integer2",
                "value": "5"
            }
        ]
    }
],
links: [
    {
        "methodName": "Main.addIntegers()",
        "source": 3,
        "target": 4
    },
    {
        "methodName": "Main.addStrings()",
        "source": 4,
        "target": 5
    },
    {
        "methodName": "Main.<init>()",
        "source": 1,
        "target": 2
    },
    {
        "methodName": "Main.addStrings()",
        "source": 5,
        "target": 6
    },
    {
        "methodName": "Main.<init>()",
        "source": 0,
        "target": 1
    },
    {
        "methodName": "Main.addIntegers()",
        "source": 2,
        "target": 3
    }
]
};

var testSimpleMonkeyData = {
    states: [
        {
            fields: [
                {
                    name: "monkey",
                    value: "tim"
                },
                {
                    name: "chimp",
                    value: "rodger"
                }
            ]
        },
        {
            fields: [
                {
                    name: "monkey",
                    value: "tom"
                },
                {
                    name: "chimp",
                    value: "rodger"
                }
            ]
        },
        {
            fields: [
                {
                    name: "monkey",
                    value: "tom"
                },
                {
                    name: "chimp",
                    value: "randy"
                }
            ]
        },
        {
            fields: [
                {
                    name: "monkey",
                    value: "timaline"
                },
                {
                    name: "chimp",
                    value: "rachael"
                }
            ]
        }
    ],
    links: [
        {
            methodName: "monkeyNameChange",
            source: 0,
            target: 1
        },
        {
            methodName: "chimpNameChange",
            source: 1,
            target: 2
        },
        {
            methodName: "sexChange",
            source: 2,
            target: 3
        }
    ]
}

viz.automata.init(JSON.stringify(testData));
//viz.petri.init(JSON.stringify(testSimpleMonkeyData));
