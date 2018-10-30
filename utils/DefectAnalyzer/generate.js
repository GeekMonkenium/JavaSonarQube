// o = old report path (till serenity ) = optional
// n = new report path (till serenity) = mandatory
// r = report directory = mandatory
// d = deliminator = optional = default is | = has to be single character
// l = column concate = optional = default is 100

const jsonFileReader = require('jsonfile');
const glob = require('glob');
const csvdata = require('csvdata');
const commandLineArgs = require('command-line-args');
const path = require('path');

const optionDefinitions = [{
    name: 'oldReportPath',
    alias: 'o',
    type: String
  },
  {
    name: 'newReportPath',
    alias: 'n',
    type: String
  },
  {
    name: 'reportDir',
    alias: 'r',
    type: String
  },
  {
    name: 'delim',
    alias: 'd',
    type: String,
    defaultValue: ','
  },
  {
    name: 'messageLength',
    alias: 'l',
    type: Number,
    defaultValue: 100
  }
]
const options = commandLineArgs(optionDefinitions);

validateCommandLineArguments();

glob(path.join(options.newReportPath, '*.json'), function (err, files) {
  var newErrors = analyseReport(err, files, "newReport.csv");
  if (options.oldReportPath) {
    glob(path.join(options.oldReportPath, '*.json'), function (err, files) {
      var oldErrors = analyseReport(err, files, "oldReport.csv");
      reportNewFailures(oldErrors, newErrors);
    });
  }
});

function reportNewFailures(oldErrorList, newErrorList) {
  var oldErrors = new Map(oldErrorList.map((i) => [i[0],
    [i[1], i[2]]
  ]));
  var newErrors = new Map(newErrorList.map((i) => [i[0],
    [i[1], i[2]]
  ]));

  var oldFailureScenarios = Array.from(oldErrors.keys());
  var newFailureScenarios = Array.from(newErrors.keys());

  var deltaFailureScenarios = newFailureScenarios.filter(el => oldFailureScenarios.indexOf(el) < 0);

  var deltaFailures = deltaFailureScenarios.map((failure) => {
    var epic = newErrors.get(failure)[0];
    var message = newErrors.get(failure)[1];
    return [failure, epic, message];
  });

  csvdata.write(path.join(options.reportDir, "output.csv"), deltaFailures, {
    delimiter: options.delim
  });
}

function analyseReport(err, files, reportName) {
  if (err) return console.error("Error reading directory", err);

  var failedJsons = files.map((jsonFile) => jsonFileReader.readFileSync(jsonFile, 'utf-8'))
    .filter((jsonObj) => jsonObj.result == 'ERROR' || jsonObj.result == 'FAILURE');

  var csv = failedJsons.map((failedJson) => {
    var summary = formatColumn(failedJson.testFailureSummary);
    var title = formatColumn(failedJson.title);
    var epicTag = failedJson.tags.find((i) => i.type == 'epic');
    var epic;
    if (epicTag) {
      epic = formatColumn(epicTag.name);
    }
    return [title, epic, summary];
  });

  csvdata.write(path.join(options.reportDir, reportName), csv, {
    delimiter: options.delim
  });
  return csv;
}

function formatColumn(data) {
  return data.replace(/(\r\n|\n|\r)/gm, "").split(options.delim).join("").substring(0, options.messageLength);
};

function validateCommandLineArguments() {
  if (!options.newReportPath) {
    console.error("New Report Path is mandatory");
    process.exit(1);
  }

  if (!options.reportDir) {
    console.error("Report Directory is mandatory");
    process.exit(1);
  }

  if (options['delim'].length > 1) {
    console.error("Delim should be single character");
    process.exit(1);
  }
}