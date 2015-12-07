'use strict';

angular.module('tiimspotApp').controller('ContactinfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Contactinfo',
        function($scope, $stateParams, $modalInstance, entity, Contactinfo) {

        $scope.contactinfo = entity;
        $scope.load = function(id) {
            Contactinfo.get({id : id}, function(result) {
                $scope.contactinfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('tiimspotApp:contactinfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.contactinfo.id != null) {
                Contactinfo.update($scope.contactinfo, onSaveSuccess, onSaveError);
            } else {
                Contactinfo.save($scope.contactinfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
